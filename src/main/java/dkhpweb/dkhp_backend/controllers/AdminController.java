package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.Admin.CreateAdminDto;
import dkhpweb.dkhp_backend.dtos.Admin.ResAdminDto;
import dkhpweb.dkhp_backend.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<ResAdminDto> addAdmin(
        @RequestBody @Valid CreateAdminDto createAdminDto) {
        return ResponseEntity.ok(adminService.addAdmin(createAdminDto));
    }

    @PatchMapping("/block/{adminId}")
    public ResponseEntity<Void> blockAdmin(
            @PathVariable String adminId,
            @RequestParam boolean isBlocked) {
        adminService.blockAdmin(adminId, isBlocked);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info")
    public ResponseEntity<ResAdminDto> getAdminInfo(){
        return ResponseEntity.ok(adminService.getAdminInfo());
    }

    @GetMapping
    public ResponseEntity<List<ResAdminDto>> getAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }
}
