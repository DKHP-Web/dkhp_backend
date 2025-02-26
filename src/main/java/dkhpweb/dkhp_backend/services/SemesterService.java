package dkhpweb.dkhp_backend.services;

import dkhpweb.dkhp_backend.dtos.Semester.CreateSemesterDto;

public interface SemesterService {
    void addSemester(CreateSemesterDto semesterDto);
}
