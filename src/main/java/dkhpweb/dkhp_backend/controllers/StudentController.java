package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.ResPageDto;
import dkhpweb.dkhp_backend.dtos.Student.CreateStudentDto;
import dkhpweb.dkhp_backend.dtos.Student.ResStudentDto;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResStudentDto> addStudent(
            @RequestBody @Valid CreateStudentDto studentDto) {
        return ResponseEntity.ok(studentService.addStudent(studentDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/block/{studentId}")
    public ResponseEntity<Void> blockStudent(
           @PathVariable String studentId,
           @RequestParam Boolean isBlocked) {
        studentService.blockStudent(studentId, isBlocked);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ResPageDto> getStudents(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize){
        return ResponseEntity.ok(studentService.getStudents(pageNum,pageSize));
    }


    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/info")
    public ResponseEntity<ResStudentDto> getStudentInfo(){
        return ResponseEntity.ok(studentService.getStudentInfo());
    }
}
