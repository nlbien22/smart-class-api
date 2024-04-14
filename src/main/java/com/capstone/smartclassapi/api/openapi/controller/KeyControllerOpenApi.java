package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.KeyRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface KeyControllerOpenApi {
    @Operation(summary = "Get a key for an exam")
    ResponseEntity<?> getKey(Long examId, Long keyId);

    @Operation(summary = "Get all keys for an exam (sort, search, pagination)")
    ResponseEntity<?> getAllKeys(Long examId, int page, int size, String keyword, String sortType, String sortValue);

    @Operation(summary = "Create a new key for an exam")
    ResponseEntity<?> createKey(Long examId, KeyRequest key);

    @Operation(summary = "Update a key for an exam")
    ResponseEntity<?> updateKey(Long examId, Long keyId, KeyRequest key);

    @Operation(summary = "Delete a key for an exam")
    ResponseEntity<?> deleteKey(Long examId, Long keyId);
}
