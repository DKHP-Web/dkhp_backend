package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.Admin.CreateAdminDto;
import dkhpweb.dkhp_backend.dtos.Admin.ResAdminDto;
import dkhpweb.dkhp_backend.models.Admin;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import dkhpweb.dkhp_backend.repositories.AdminRepository;
import dkhpweb.dkhp_backend.services.AdminService;
import dkhpweb.dkhp_backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepo;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Override
    public ResAdminDto addAdmin(CreateAdminDto adminDto) {
        var user= authService.createUser(adminDto.getUser(), UserRole.ADMIN);
        var admin= Admin.builder()
                .name(adminDto.getName())
                .role(AdminRole.ASSISTANT)
                .user(user)
                .build();
        var savedAdmin=adminRepo.save(admin);
        return modelMapper.map(savedAdmin, ResAdminDto.class);
    }
}
