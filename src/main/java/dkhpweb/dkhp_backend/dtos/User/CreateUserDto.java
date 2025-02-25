package dkhpweb.dkhp_backend.dtos.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserDto {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message="Password must be at least 1 number, 1 special character, 1 uppercase letter, 1 lowercase letter")
    private String password;
}
