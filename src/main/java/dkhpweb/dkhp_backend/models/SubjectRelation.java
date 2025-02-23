package dkhpweb.dkhp_backend.models;

import dkhpweb.dkhp_backend.models.enums.SubjectRelationType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubjectRelation {
	@EmbeddedId
	private SubjectRelationKey id=new SubjectRelationKey();

	@ManyToOne
	@MapsId("currSubjectId")
	private Subject currSubject;

	@ManyToOne
	@MapsId("preSubjectId")
	private Subject preSubject;

	private SubjectRelationType type;
}
