package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.Semester.CreateSemesterDto;
import dkhpweb.dkhp_backend.dtos.Semester.ResSemesterDto;
import dkhpweb.dkhp_backend.models.Semester;
import dkhpweb.dkhp_backend.repositories.SemesterRepository;
import dkhpweb.dkhp_backend.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResSemesterDto addSemester(CreateSemesterDto semesterDto) {
        var semester= modelMapper.map(semesterDto, Semester.class);
        var savedSemester=semesterRepo.save(semester);
        return modelMapper.map(savedSemester, ResSemesterDto.class);
    }
}
