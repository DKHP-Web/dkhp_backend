package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.dtos.ApiResult;
import dkhpweb.dkhp_backend.dtos.ResPageDto;
import dkhpweb.dkhp_backend.dtos.Student.CreateStudentDto;
import dkhpweb.dkhp_backend.dtos.Student.ResStudentDto;
import dkhpweb.dkhp_backend.models.Student;
import dkhpweb.dkhp_backend.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResult<ResStudentDto>> addStudent(
            @RequestBody @Valid CreateStudentDto studentDto) {
        var result=studentService.addStudent(studentDto);
        return ResponseEntity.ok(ApiResult.succeed(result));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/block/{studentId}")
    public ResponseEntity<ApiResult<ResStudentDto>> blockStudent(
           @PathVariable String studentId) {
        studentService.blockStudent(studentId);
        return ResponseEntity.ok(ApiResult.succeedBodiless());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResult<ResPageDto>> getStudents(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize){
        var result=studentService.getStudents(pageNum,pageSize);
        return ResponseEntity.ok(ApiResult.succeed(result));
    }


    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/info")
    public ResponseEntity<ApiResult<ResPageDto>> getStudentInfo(){
        var result=studentService.getStudentInfo();
        return ResponseEntity.ok(ApiResult.succeed(result));
    }
}
