package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.StudentRequest;
import com.capstone.smartclassapi.api.openapi.controller.StudentControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class/{classId}/student")
@RequiredArgsConstructor
public class StudentController implements StudentControllerOpenApi {
    private final String DEFAULT_SORT_VALUE = "full_name";
    private final StudentService studentService;


    @GetMapping("/{studentId}")
    @Override
    public ResponseEntity<?> getStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudent(classId, studentId));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllStudents(
        @PathVariable Long classId,
        @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
        @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "") String sortType,
        @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getAllStudents(classId, page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createStudent(@PathVariable Long classId, @RequestBody StudentRequest student) {
        studentService.createStudent(classId, student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/list")
    @Override
    public ResponseEntity<?> createManyStudents(@PathVariable Long classId, @RequestBody List<StudentRequest> students) {
        studentService.createManyStudents(classId, students);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    @Override
    public ResponseEntity<?> updateStudent(@PathVariable Long classId, @PathVariable Long studentId, @RequestBody StudentRequest student) {
        return null;
    }

    @DeleteMapping("/{studentId}")
    @Override
    public ResponseEntity<?> deleteStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        studentService.deleteStudent(classId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteAllStudents(@PathVariable Long classId) {
        studentService.deleteAllStudents(classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
