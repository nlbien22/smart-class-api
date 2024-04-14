package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.dto.request.SubjectRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface SubjectControllerOpenApi {
    @Operation(summary = "Get a subject by id")
    ResponseEntity<?> getSubject(Long subjectId);

    @Operation(summary = "Get all subjects (sort, search, pagination)")
    ResponseEntity<?> getAllSubjects(int page, int size, String keyword, String sortType, String sortValue);

    @Operation(summary = "Create a new subject")
    ResponseEntity<?> createSubject(SubjectRequest request);

    @Operation(summary = "Update a subject")
    ResponseEntity<?> updateSubject(Long subjectId, SubjectRequest request);

    @Operation(summary = "Delete a subject")
    ResponseEntity<?> deleteSubject(Long subjectId);
}
