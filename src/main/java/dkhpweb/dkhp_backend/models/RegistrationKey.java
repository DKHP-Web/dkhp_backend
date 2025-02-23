package dkhpweb.dkhp_backend.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class RegistrationKey {
	private String studentId;
	private String courseId;
}
