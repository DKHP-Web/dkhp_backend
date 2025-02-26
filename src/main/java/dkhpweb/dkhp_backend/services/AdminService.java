package dkhpweb.dkhp_backend.services;

import dkhpweb.dkhp_backend.dtos.Admin.CreateAdminDto;
import dkhpweb.dkhp_backend.dtos.Admin.ResAdminDto;

import java.util.List;

public interface AdminService {
    ResAdminDto addAdmin(CreateAdminDto adminDto);
    void blockAdmin(String adminId, boolean isBlocked);
    ResAdminDto getAdminInfo();
    List<ResAdminDto> getAllAdmins();
}
