package dkhpweb.dkhp_backend.services;

import dkhpweb.dkhp_backend.dtos.ResPageDto;
import dkhpweb.dkhp_backend.dtos.Student.CreateStudentDto;
import dkhpweb.dkhp_backend.dtos.Student.ResStudentDto;

public interface StudentService {
    ResStudentDto addStudent(CreateStudentDto studentDto);
    void blockStudent(String studentId);
    ResStudentDto getStudentInfo();
    ResPageDto<ResStudentDto> getStudents(Integer pageNum, Integer pageSize);
}
