package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.Semester.CreateSemesterDto;
import dkhpweb.dkhp_backend.repositories.SemesterRepository;
import dkhpweb.dkhp_backend.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepo;
    private

    @Override
    public void addSemester(CreateSemesterDto semesterDto) {

    }
}
