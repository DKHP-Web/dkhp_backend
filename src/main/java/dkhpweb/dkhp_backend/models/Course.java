package dkhpweb.dkhp_backend.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Column(unique=true)
	private String courseId;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate beginDate;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate endDate;

	private String language;

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

	@ManyToOne
	@JoinColumn(name="subjectId")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name="mainCourseId")
	private Course mainCourse;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Registration> registrations;
}
