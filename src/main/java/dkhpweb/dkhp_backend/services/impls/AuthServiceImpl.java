package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.Auth.ResLoginDto;
import dkhpweb.dkhp_backend.repositories.UserRepository;
import dkhpweb.dkhp_backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;

    @Override
    public ResLoginDto login(String email, String password) {
        return null;
    }

    @Override
    public void sendOtpCode(String email) {

    }

    @Override
    public void resetPassword(String email, String otpCode) {

    }
}
