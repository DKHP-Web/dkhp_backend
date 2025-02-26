package dkhpweb.dkhp_backend.dtos.Admin;

import dkhpweb.dkhp_backend.dtos.User.ResUserDto;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import lombok.Data;

@Data
public class ResAdminDto {
    private String id;

    private String name;

    private AdminRole role;

    private ResUserDto user;
}
