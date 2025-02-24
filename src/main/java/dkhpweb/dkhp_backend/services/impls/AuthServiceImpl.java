package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.exceptions.BadRequestException;
import dkhpweb.dkhp_backend.repositories.UserRepository;
import dkhpweb.dkhp_backend.services.AuthService;
import dkhpweb.dkhp_backend.utils.JwtUtil;
import dkhpweb.dkhp_backend.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailUtil mailUtil;
    SecureRandom random = new SecureRandom();

    @Override
    public ResLoginDto login(String email, String password) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new BadRequestException("Email or password is incorrect"));
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new BadRequestException("Email or password is incorrect");

        String accessToken= jwtUtil.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken= jwtUtil.generateToken(user, TokenType.REFRESH_TOKEN);
        return new ResLoginDto(accessToken, refreshToken);
    }

    @Override
    public String refreshToken(String refreshToken) {
        var userId= jwtUtil.getUserIdFromRefreshToken(refreshToken);

        var user= userRepo.findById(userId)
                .orElseThrow(()-> new AuthorizationDeniedException("JWT token was exprired or incorrect"));
        return jwtUtil.generateToken(user, TokenType.ACCESS_TOKEN);
    }

    @Override
    public void sendOtpCode(String email) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new BadRequestException("Email not found"));

        String otpCode= ""+(100_000 + random.nextInt(900_000));
        var otpTime= LocalDateTime.now().plusMinutes(5);

        user.setOtpCode(otpCode);
        user.setOtpTime(otpTime);
        userRepo.save(user);

        mailUtil.sendMail(email, "[OTP Code]",
                "Hi <b>"+user.getName()+"<b/>, here is your OTP Code: <b>"+otpCode+"<b/>" +
                        ". Please enter it within 5 minutes");
    }

    @Override
    public void resetPassword(String email, String otpCode, String newPassword) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new BadRequestException("Email not found"));

        var now= LocalDateTime.now();
        if(user.getOtpTime()==null || user.getOtpTime().isAfter(now))
            throw new BadRequestException("OTP code is expired or incorrect");
        if(user.getOtpCode()==null|| !user.getOtpCode().equals(otpCode))
            throw new BadRequestException("OTP code is expired or incorrect");

        user.setOtpTime(null);
        user.setOtpCode(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
