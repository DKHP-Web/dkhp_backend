package dkhpweb.dkhp_backend.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Semester {
	@Id @UuidGenerator
	private String id;

	private Integer semesterNum;

	private Integer year;
}
