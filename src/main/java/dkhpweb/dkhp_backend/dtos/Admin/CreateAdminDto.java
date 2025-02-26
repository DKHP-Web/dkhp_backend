package dkhpweb.dkhp_backend.dtos.Admin;

import dkhpweb.dkhp_backend.dtos.User.CreateUserDto;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAdminDto {
    @NotBlank
    private String name;

    @Valid
    private CreateUserDto user;
}
