package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.Auth.ReqLoginDto;
import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.dtos.Auth.ResetPasswordDto;
import dkhpweb.dkhp_backend.dtos.Auth.ResetTempPasswordDto;
import dkhpweb.dkhp_backend.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResLoginDto> login(
            @RequestBody @Valid ReqLoginDto loginDto){
        var result= authService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(
            @RequestBody String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PostMapping("/otp-code/{email}")
    public ResponseEntity<Void> sendOtpCode(
            @PathVariable @Email String email){
        authService.sendOtpCode(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody @Valid ResetPasswordDto resetPasswordDto){
        authService.resetPassword(
                resetPasswordDto.getEmail(),
                resetPasswordDto.getOtpCode(),
                resetPasswordDto.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-temp-password")
    public ResponseEntity<Void> resetTempPassword(
            @RequestBody @Valid ResetTempPasswordDto resetTempPasswordDto){
        authService.resetTempPassword(
                resetTempPasswordDto.getTempPasswordToken(),
                resetTempPasswordDto.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}
