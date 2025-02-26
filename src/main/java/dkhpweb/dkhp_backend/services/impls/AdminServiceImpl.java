package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.Admin.CreateAdminDto;
import dkhpweb.dkhp_backend.dtos.Admin.ResAdminDto;
import dkhpweb.dkhp_backend.exceptions.BadRequestException;
import dkhpweb.dkhp_backend.models.Admin;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import dkhpweb.dkhp_backend.repositories.AdminRepository;
import dkhpweb.dkhp_backend.services.AdminService;
import dkhpweb.dkhp_backend.services.AuthService;
import dkhpweb.dkhp_backend.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepo;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Override
    public ResAdminDto addAdmin(CreateAdminDto adminDto) {
        if(!isMainAdmin())
            throw new AccessDeniedException("You don't have permission to block admin");

        var user= authService.createUser(adminDto.getUser(), UserRole.ADMIN);
        var admin= Admin.builder()
                .name(adminDto.getName())
                .role(AdminRole.ASSISTANT)
                .user(user)
                .build();
        var savedAdmin=adminRepo.save(admin);
        return modelMapper.map(savedAdmin, ResAdminDto.class);
    }

    @Override
    public void blockAdmin(String adminId, boolean isBlocked) {
        if(!isMainAdmin())
            throw new AccessDeniedException("You don't have permission to block admin");

        var admin= adminRepo.findById(adminId)
                .orElseThrow(()-> new BadRequestException("Admin not found"));
        admin.getUser().setIsBlocked(isBlocked);
        adminRepo.save(admin);
    }

    @Override
    public ResAdminDto getAdminInfo() {
        String userId= AuthUtil.getUserId();
        var admin= adminRepo.findByUserId(userId);
        return modelMapper.map(admin, ResAdminDto.class);
    }

    @Override
    public List<ResAdminDto> getAllAdmins() {
        if(!isMainAdmin())
            throw new AccessDeniedException("You don't have permission to block admin");

        var adminsList= adminRepo.findAll();
        return adminsList.stream()
                .map(admin->modelMapper.map(admin, ResAdminDto.class))
                .toList();
    }

    private boolean isMainAdmin(){
        String userId= AuthUtil.getUserId();
        var admin= adminRepo.findByUserId(userId);
        return admin.getRole()==AdminRole.MAIN;
    }
}
