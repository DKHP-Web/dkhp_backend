package dkhpweb.dkhp_backend.dtos.Student;

import dkhpweb.dkhp_backend.dtos.User.CreateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateStudentDto {
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "StudentId must be exactly 8 digits")
    private String id;

    @NotBlank
    private String falcutyName;

    @NotBlank
    private String program;

    @NotNull
    private Integer admissionYear;

    @NotNull
    @Valid
    private CreateUserDto user;
}
