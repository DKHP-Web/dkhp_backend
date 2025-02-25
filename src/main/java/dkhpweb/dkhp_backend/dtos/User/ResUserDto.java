package dkhpweb.dkhp_backend.dtos.User;

import dkhpweb.dkhp_backend.models.enums.UserRole;
import lombok.Data;

@Data
public class ResUserDto {
    private String id;

    private String email;

    private UserRole role;
}
