package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.StudentRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentControllerOpenApi {
    @Operation(summary = "Get a student by studentId")
    ResponseEntity<?> getStudent(Long classId, Long studentId);
    @Operation(summary = "Get all students")
    ResponseEntity<?> getAllStudents(Long classId, int page, int size, String keyword, String sortType, String sortValue);
    @Operation(summary = "Create a student")
    ResponseEntity<?> createStudent(Long classId, StudentRequest student);
    @Operation(summary = "Create many students")
    ResponseEntity<?> createManyStudents(Long classId, List<StudentRequest> students);
    @Operation(summary = "Update a student")
    ResponseEntity<?> updateStudent(Long classId, Long studentId, StudentRequest student);
    @Operation(summary = "Delete a student")
    ResponseEntity<?> deleteStudent(Long classId, Long studentId);
    @Operation(summary = "Delete all students")
    ResponseEntity<?> deleteAllStudents(Long classId);
}
