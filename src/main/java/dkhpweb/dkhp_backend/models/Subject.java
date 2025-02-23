package dkhpweb.dkhp_backend.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {
	@Id
	private String id;

	@Column(unique=true)
	private String name;

	private Integer theoryCreditNumber;

	private Integer practiceCreditNumber;

	@OneToMany(mappedBy="subject",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	List<Course> courses;

	@OneToMany(mappedBy="currSubject",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	List<SubjectRelation> relations;
}
