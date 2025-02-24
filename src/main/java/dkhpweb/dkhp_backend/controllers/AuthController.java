package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.ApiResult;
import dkhpweb.dkhp_backend.dtos.Auth.ReqLoginDto;
import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.dtos.Auth.ResetPasswordDto;
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
    public ResponseEntity<ApiResult<ResLoginDto>> login(
            @RequestBody @Valid ReqLoginDto loginDto){
        var result= authService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(ApiResult.succeed(result));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<String>> refreshToken(
            @RequestBody String refreshToken){
        var result= authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResult.succeed(result));
    }

    @PostMapping("/otp-code/{email}")
    public ResponseEntity<ApiResult<String>> sendOtpCode(
            @PathVariable @Email String email){
        authService.sendOtpCode(email);
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResult<String>> resetPassword(
            @RequestBody @Valid ResetPasswordDto resetPasswordDto){
        authService.resetPassword(
                resetPasswordDto.getEmail(),
                resetPasswordDto.getOtpCode(),
                resetPasswordDto.getNewPassword());
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }
}
