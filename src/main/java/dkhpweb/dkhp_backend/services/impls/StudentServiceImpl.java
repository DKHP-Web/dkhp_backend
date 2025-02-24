package dkhpweb.dkhp_backend.services.impls;

import dkhpweb.dkhp_backend.dtos.ResPageDto;
import dkhpweb.dkhp_backend.dtos.Pagination;
import dkhpweb.dkhp_backend.dtos.Student.CreateStudentDto;
import dkhpweb.dkhp_backend.dtos.Student.ResStudentDto;
import dkhpweb.dkhp_backend.exceptions.BadRequestException;
import dkhpweb.dkhp_backend.models.Student;
import dkhpweb.dkhp_backend.models.User;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import dkhpweb.dkhp_backend.repositories.StudentRepository;
import dkhpweb.dkhp_backend.repositories.UserRepository;
import dkhpweb.dkhp_backend.services.StudentService;
import dkhpweb.dkhp_backend.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResStudentDto addStudent(CreateStudentDto studentDto) {
        if(studentRepo.existsById(studentDto.getId()))
            throw new BadRequestException("StudentId"+studentDto.getId()+" already exists");
        if(userRepo.existsByEmail(studentDto.getUser().getEmail()))
            throw new BadRequestException("UserEmail"+studentDto.getUser().getEmail()+" already exists");

        Student student=modelMapper.map(studentDto, Student.class);

        var userDto= studentDto.getUser();
        var user= User.builder()
                .email(userDto.getEmail())
                .role(UserRole.STUDENT)
                .isBlocked(false)
                .build();
        if(userDto.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setIsActived(true);
        }
        else {
            user.setIsActived(false);
        }
        student.setUser(user);

        var savedStudent=studentRepo.save(student);

        return modelMapper.map(savedStudent, ResStudentDto.class);
    }

    public void blockStudent(String studentId){
        var student= studentRepo.findById(studentId).orElseThrow(()-> new BadRequestException("Student not found"));
        student.getUser().setIsBlocked(true);
        studentRepo.save(student);
    }

    public ResStudentDto getStudentInfo(){
        var userId= AuthUtil.getUserId();
        var student= studentRepo.findByUser(userRepo.getById(userId))
                .orElseThrow(()-> new BadRequestException("Student not found"));
        return modelMapper.map(student, ResStudentDto.class);
    }

    public ResPageDto<ResStudentDto> getStudents(Integer pageNum, Integer pageSize){
        var userId= AuthUtil.getUserId();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        var studentsPage= studentRepo.findByUser(userRepo.getById(userId), pageRequest);

        var studentDtos= studentsPage.stream()
                    .map(student->modelMapper.map(student, ResStudentDto.class))
                    .toList();
        var pagination=new Pagination(studentsPage.getNumber(), studentsPage.getSize(), studentsPage.getTotalElements());

        return new ResPageDto(studentDtos, pagination);
    }
}
