package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.dto.response.GradedResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public interface GradedControllerOpenApi {
    @Operation(summary = "Get graded of an exam")
    ResponseEntity<?> getGraded(Long examId, Long gradedId);

    @Operation(summary = "Get all graded of an exam (sort, search, pagination)")
    ResponseEntity<?> getAllGrades(Long examId, int page, int size, String keyword, String sortType, String sortValue);

    @Operation(summary = "Create a new graded for an exam")
    ResponseEntity<?> createGraded(Long examId, GradedResponse request);

    @Operation(summary = "Update a graded for an exam")
    ResponseEntity<?> updateGraded(Long examId, Long gradedId, GradedResponse request);

    @Operation(summary = "Delete a graded for an exam")
    ResponseEntity<?> deleteGraded(Long examId, Long gradedId);

    @Operation(summary = "Delete all graded for an exam")
    ResponseEntity<?> deleteAllGraded(Long examId);
}
