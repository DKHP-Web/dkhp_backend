package dkhpweb.dkhp_backend.services;

import dkhpweb.dkhp_backend.dtos.Admin.CreateAdminDto;
import dkhpweb.dkhp_backend.dtos.Admin.ResAdminDto;

public interface AdminService {
    ResAdminDto addAdmin(CreateAdminDto adminDto);
}
