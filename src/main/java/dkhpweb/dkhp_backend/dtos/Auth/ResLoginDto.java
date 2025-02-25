package dkhpweb.dkhp_backend.dtos.Auth;

import dkhpweb.dkhp_backend.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResLoginDto {
    private String accessToken;

    private String refreshToken;

    private String tempPasswordToken;
}
