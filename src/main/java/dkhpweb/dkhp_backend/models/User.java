package dkhpweb.dkhp_backend.models;

import dkhpweb.dkhp_backend.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id @UuidGenerator
	private String id;

	@Column(nullable=false, unique=true)
	private String email;

	private String password;

	private Boolean isTempPassword;

	@Column(columnDefinition = "TEXT")
	private String refreshToken;

	@Column(columnDefinition = "TEXT")
	private String tempPasswordToken;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String otpCode;

	private LocalDateTime otpTime;

	private Boolean isBlocked;
}
