package dkhpweb.dkhp_backend.models;

import dkhpweb.dkhp_backend.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	private String id;

	@Column(nullable=false, unique=true)
	private String email;

	private String name;

	@Column(nullable=false, unique=true)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String otpCode;

	private LocalDateTime otpTime;

	@Column(nullable = false)
	private Boolean isBlocked;
}
