package dkhpweb.dkhp_backend.services;

import dkhpweb.dkhp_backend.dtos.Semester.CreateSemesterDto;
import dkhpweb.dkhp_backend.dtos.Semester.ResSemesterDto;

public interface SemesterService {
    ResSemesterDto addSemester(CreateSemesterDto semesterDto);
}
