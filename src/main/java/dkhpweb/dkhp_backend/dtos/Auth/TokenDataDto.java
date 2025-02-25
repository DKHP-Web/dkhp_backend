package dkhpweb.dkhp_backend.dtos.Auth;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDataDto {
    private String userId;

    private UserRole role;

    private TokenType tokenType;
}
