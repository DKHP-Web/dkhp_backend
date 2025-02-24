package dkhpweb.dkhp_backend.dtos.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto {
    @Email
    @NotBlank
    private String email;

    private String password;
}
