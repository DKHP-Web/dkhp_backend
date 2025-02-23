package dkhpweb.dkhp_backend.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class SubjectRelationKey implements Serializable{
	private String currSubjectId;
	private String preSubjectId;
}
