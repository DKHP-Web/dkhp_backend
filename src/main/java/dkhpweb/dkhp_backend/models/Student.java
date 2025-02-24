package dkhpweb.dkhp_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
	@Id @UuidGenerator
	private String id;

	@Column(nullable = false)
	private String falcutyName;

	@Column(nullable = false)
	private String program;

	private Integer admissionYear;

	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(nullable=false)
	private User user;
}
