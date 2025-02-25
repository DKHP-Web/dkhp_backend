package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.ApiResult;
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
    public ResponseEntity<ApiResult> login(
            @RequestBody @Valid ReqLoginDto loginDto){
        ResLoginDto result= authService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(ApiResult.succeed(result));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResult> refreshToken(
            @RequestBody String refreshToken){
        String result= authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResult.succeed(result));
    }

    @PostMapping("/otp-code/{email}")
    public ResponseEntity<ApiResult> sendOtpCode(
            @PathVariable @Email String email){
        authService.sendOtpCode(email);
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResult> resetPassword(
            @RequestBody @Valid ResetPasswordDto resetPasswordDto){
        authService.resetPassword(
                resetPasswordDto.getEmail(),
                resetPasswordDto.getOtpCode(),
                resetPasswordDto.getNewPassword());
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }

    @PostMapping("/reset-temp-password")
    public ResponseEntity<ApiResult> resetTempPassword(
            @RequestBody @Valid ResetTempPasswordDto resetTempPasswordDto){
        authService.resetTempPassword(
                resetTempPasswordDto.getTempPasswordToken(),
                resetTempPasswordDto.getNewPassword());
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }
}
