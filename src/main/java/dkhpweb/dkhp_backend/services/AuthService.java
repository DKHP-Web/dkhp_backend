package dkhpweb.dkhp_backend.services;


import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;

public interface AuthService {
    ResLoginDto login(String email, String password);
    void sendOtpCode(String email);
    void resetPassword(String email, String otpCode);
}
