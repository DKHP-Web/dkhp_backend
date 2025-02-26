package dkhpweb.dkhp_backend.controllers;

import dkhpweb.dkhp_backend.dtos.Semester.CreateSemesterDto;
import dkhpweb.dkhp_backend.dtos.Semester.ResSemesterDto;
import dkhpweb.dkhp_backend.services.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semester")
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService semesterService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResSemesterDto> addSemester(
            @RequestBody @Valid CreateSemesterDto semesterDto) {
        return ResponseEntity.ok(semesterService.addSemester(semesterDto));
    }
}
