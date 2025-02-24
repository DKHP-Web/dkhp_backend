package dkhpweb.dkhp_backend.dtos.Auth;

import dkhpweb.dkhp_backend.models.enums.UserRole;
import lombok.Data;

@Data
public class ResLoginDto {
    private String token;

    private UserRole role;
}
