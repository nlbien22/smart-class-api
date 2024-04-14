package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface ExamControllerOpenApi {
    @Operation(summary = "Get an exam for of user")
    ResponseEntity<?> getExam(Long examId);

    @Operation(summary = "Get all exams for a user (sort, search, pagination)")
    ResponseEntity<?> getAllExams(int page, int size, String keyword, String sortType, String sortValue);

    @Operation(summary = "Create a new exam for a user")
    ResponseEntity<?> createExam(ExamRequest exam);

    @Operation(summary = "Update an exam for a user")
    ResponseEntity<?> updateExam(Long examId, ExamRequest exam);

    @Operation(summary = "Delete an exam for a user")
    ResponseEntity<?> deleteExam(Long examId);


}
