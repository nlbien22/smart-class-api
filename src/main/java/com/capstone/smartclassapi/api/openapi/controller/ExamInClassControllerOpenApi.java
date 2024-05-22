package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ExamInClassControllerOpenApi {
    @Operation(summary = "Get an exam for a class")
    ResponseEntity<?> getExamsOfClass(Long classId, Long examId);

    @Operation(summary = "Get all exams for a class (sort, search, pagination)")
    ResponseEntity<?> getAllExamsOfClass(Long classId, int page, int size, String keyword, String sortType, String sortValue);

    @Operation(summary = "Create a new exam for a class")
    ResponseEntity<?> createExamOfClass(Long classId, ExamRequest exam);

    @Operation(summary = "Update an exam for a class")
    ResponseEntity<?> updateExamOfClass(Long classId, Long examId, ExamRequest exam);

    @Operation(summary = "Delete an exam for a class")
    ResponseEntity<?> deleteExamOfClass(Long classId, Long examId);

    @Operation(summary = "Delete all exams for a class")
    public ResponseEntity<?> deleteAllExamsOfClass(Long classId);
}
