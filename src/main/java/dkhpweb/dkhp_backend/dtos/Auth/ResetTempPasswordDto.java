package dkhpweb.dkhp_backend.dtos.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetTempPasswordDto {
    @NotBlank
    private String tempPasswordToken;

    @NotBlank
    private String newPassword;
}
