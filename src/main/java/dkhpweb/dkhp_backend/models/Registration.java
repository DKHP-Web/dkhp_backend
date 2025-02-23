package dkhpweb.dkhp_backend.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Registration {
	@EmbeddedId
	private RegistrationKey id;

	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("studentId")
	private Student student;

	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("courseId")
	private Course course;
}
