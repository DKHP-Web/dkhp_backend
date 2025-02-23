package dkhpweb.dkhp_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RegistrationPeriod {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime openTime;

	private LocalDateTime closeTime;

	@ManyToOne
	private Semester semester;
}
