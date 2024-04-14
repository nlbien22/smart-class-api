package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.CloRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface CloControllerOpenApi {
    @Operation(summary = "Get a Clo by id")
    ResponseEntity<?> getClo(Long subjectId, Long cloId);
    @Operation(summary = "Get all clos (sort, search, pagination)")
    ResponseEntity<?> getClos(Long subjectId, int page, int size,
                                    String keyword,
                                    String sortType,
                                    String sortValue);
    @Operation(summary = "Create a new Clo")
    ResponseEntity<?> createClo(Long subjectId, CloRequest request);
    @Operation(summary = "Update a Clo")
    ResponseEntity<?> updateClo(Long subjectId, Long cloId, CloRequest request);
    @Operation(summary = "Delete a Clo")
    ResponseEntity<?> deleteClo(Long subjectId, Long cloId);
}
