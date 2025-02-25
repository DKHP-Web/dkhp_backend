package dkhpweb.dkhp_backend.services;


import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.dtos.User.CreateUserDto;
import dkhpweb.dkhp_backend.models.User;
import dkhpweb.dkhp_backend.models.enums.UserRole;

public interface AuthService {
    User createUser(CreateUserDto userDto, UserRole userRole);
    ResLoginDto login(String email, String password);
    String refreshToken(String refreshToken);
    void sendOtpCode(String email);
    void resetPassword(String email, String otpCode, String newPassword);
    void resetTempPassword(String tempPasswordToken, String newPassword);
}
