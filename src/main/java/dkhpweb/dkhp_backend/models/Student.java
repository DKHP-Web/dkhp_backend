package dkhpweb.dkhp_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
	@Id
	private String id;

	@Column(nullable = false)
	private String falcutyName;

	@Column(nullable = false)
	private String program;

	private Integer admissionYear;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable=false)
	private User user;
}
