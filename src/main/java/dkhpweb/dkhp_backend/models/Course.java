package dkhpweb.dkhp_backend.models;

import java.time.LocalDate;
import java.util.List;

import dkhpweb.dkhp_backend.models.enums.Language;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
	@Id
	private String id;

	private LocalDate beginDate;

	private LocalDate endDate;

	private Language language;

	private Integer beginShift;

	private Integer endShift;

	private Integer dayOfWeek;

	private Integer totalNumber;

	private Integer registeredNumber=0;

	private Integer weekDistance;

	@ManyToOne(fetch=FetchType.LAZY)
	private Semester semester;

	private String room;

	private String lecturerName;

	@ManyToOne(fetch=FetchType.LAZY)
	private Subject subject;

	@ManyToOne(fetch=FetchType.LAZY)
	private Course mainCourse;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Registration> registrations;
}
