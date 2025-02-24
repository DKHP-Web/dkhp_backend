package dkhpweb.dkhp_backend.dtos.Student;

import dkhpweb.dkhp_backend.dtos.User.ResUserDto;
import lombok.Data;

@Data
public class ResStudentDto {
    private String id;

    private String falcutyName;

    private String program;

    private Integer admissionYear;

    private ResUserDto user;
}
