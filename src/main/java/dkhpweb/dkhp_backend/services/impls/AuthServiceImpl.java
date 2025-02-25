package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.dtos.User.CreateUserDto;
import dkhpweb.dkhp_backend.exceptions.BadRequestException;
import dkhpweb.dkhp_backend.models.User;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import dkhpweb.dkhp_backend.repositories.UserRepository;
import dkhpweb.dkhp_backend.services.AuthService;
import dkhpweb.dkhp_backend.utils.JwtUtil;
import dkhpweb.dkhp_backend.utils.MailUtil;
import dkhpweb.dkhp_backend.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailUtil mailUtil;
    private final PasswordUtil passwordUtil;

    @Value("${otp.expiration}")
    private Integer otpExpiration;

    @Override
    public User createUser(CreateUserDto userDto, UserRole userRole) {
        var user= User.builder()
                .email(userDto.getEmail())
                .role(userRole)
                .isTempPassword(true)
                .isBlocked(false)
                .build();
        if(userDto.getPassword()==null){
            userDto.setPassword(passwordUtil.generatePassword(8));
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String mailContent= MessageFormat.format(
                "Hi {0}, your email acocunt ha sbeen added to our registration website.</br> " +
                        "The temporary password is <b>{1}</b>. Please don't share it to any others",
                userDto.getEmail(), userDto.getPassword());
        mailUtil.sendMail(userDto.getEmail(), "[Temporary Password]", mailContent);

        return user;
    }

    @Override
    public ResLoginDto login(String email, String password) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new AccessDeniedException("Email or password is incorrect"));
        if(user.getIsBlocked())
            throw new AccessDeniedException("This account is blocked");
        if(user.getPassword()==null || !passwordEncoder.matches(password, user.getPassword()))
            throw new AccessDeniedException("Email or password is incorrect");

        if(user.getIsTempPassword()!=null&&!user.getIsTempPassword()){
            String accessToken= jwtUtil.generateToken(user, TokenType.ACCESS_TOKEN);
            String refreshToken= jwtUtil.generateToken(user, TokenType.REFRESH_TOKEN);

            user.setRefreshToken(refreshToken);
            userRepo.save(user);

            return new ResLoginDto(accessToken, refreshToken, null);
        }
        else{
            String temPasswordToken= jwtUtil.generateToken(user, TokenType.TEMP_PASSWORD);

            user.setTempPasswordToken(temPasswordToken);
            userRepo.save(user);

            return new ResLoginDto(null, null,temPasswordToken);
        }
    }

    @Override
    public String refreshToken(String refreshToken) {
        var tokenData= jwtUtil.getDataFromToken(refreshToken);
        if(tokenData.getTokenType()!=TokenType.REFRESH_TOKEN)
            throw new AccessDeniedException("Refresh token is invalid");

        var user= userRepo.findById(tokenData.getUserId())
                .orElseThrow(()-> new AccessDeniedException("JWT token was exprired or incorrect"));
        if(!refreshToken.equals(user.getRefreshToken()))
            throw new AccessDeniedException("Refresh token is invalid");

        return jwtUtil.generateToken(user, TokenType.ACCESS_TOKEN);
    }

    @Override
    public void sendOtpCode(String email) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new BadRequestException("Email not found"));

        String otpCode= passwordUtil.generateOtpCode();
        var otpTime= LocalDateTime.now().plusMinutes(otpExpiration);

        user.setOtpCode(otpCode);
        user.setOtpTime(otpTime);
        userRepo.save(user);

        String mailContent= MessageFormat.format(
                "Here is your OTP Code: <b>{0}<b/>. Please enter it within {1} minutes",
                otpCode, otpExpiration);
        mailUtil.sendMail(email, "[OTP Code]", mailContent);
    }

    @Override
    public void resetPassword(String email, String otpCode, String newPassword) {
        var user= userRepo.findByEmail(email)
                .orElseThrow(()-> new AccessDeniedException("Email not found"));
        if(user.getIsTempPassword()==null || user.getIsTempPassword())
            throw new AccessDeniedException("Accessed Denied");

        var now= LocalDateTime.now();
        if(user.getOtpTime()==null || now.isAfter(user.getOtpTime()))
            throw new AccessDeniedException("OTP code is expired or incorrect");
        if(user.getOtpCode()==null|| !user.getOtpCode().equals(otpCode))
            throw new AccessDeniedException("OTP code is expired or incorrect");

        user.setOtpTime(null);
        user.setOtpCode(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    public void resetTempPassword(String tempPasswordToken, String newPassword) {
        var tokenData= jwtUtil.getDataFromToken(tempPasswordToken);
        if(tokenData.getTokenType()!=TokenType.TEMP_PASSWORD)
            throw new AccessDeniedException("Temp password token is invalid");

        var user= userRepo.findById(tokenData.getUserId())
                .orElseThrow(()-> new AccessDeniedException("Temp password token is invalid"));
        if(!tempPasswordToken.equals(user.getTempPasswordToken()))
            throw new AccessDeniedException("Temp password token is invalid");

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTempPasswordToken(null);
        user.setIsTempPassword(false);
        userRepo.save(user);
    }
}
