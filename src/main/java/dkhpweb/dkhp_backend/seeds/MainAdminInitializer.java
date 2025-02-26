package dkhpweb.dkhp_backend.seeds;

import dkhpweb.dkhp_backend.models.Admin;
import dkhpweb.dkhp_backend.models.User;
import dkhpweb.dkhp_backend.models.enums.AdminRole;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import dkhpweb.dkhp_backend.repositories.AdminRepository;
import dkhpweb.dkhp_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainAdminInitializer implements CommandLineRunner{
	private final UserRepository userRepo;
	private final AdminRepository adminRepo;
	private final PasswordEncoder passwordEncoder;

	@Value("${main-admin.name}")
	private String adminName;
	@Value("${main-admin.email}")
	private String adminEmail;
	@Value("${main-admin.password}")
	private String adminPassword;

	@Override
	public void run(String... args) throws Exception {
		if(!adminRepo.existsByRole(AdminRole.MAIN)) {
			var user= User.builder()
					.email(adminEmail)
					.password(passwordEncoder.encode(adminPassword))
					.isTempPassword(false)
					.role(UserRole.ADMIN)
					.isBlocked(false)
					.build();

			var admin= Admin.builder()
					.name(adminName)
					.role(AdminRole.MAIN)
					.user(user)
					.build();
			adminRepo.save(admin);
		}
	}
}