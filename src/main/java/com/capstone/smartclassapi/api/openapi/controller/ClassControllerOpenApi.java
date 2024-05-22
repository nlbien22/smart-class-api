package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.ClassRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface ClassControllerOpenApi {

    @Operation(summary = "Get a class by id")
    ResponseEntity<?> getClass(Long id);

    @Operation(summary = "Get all classes (sort, search, pagination)")
    ResponseEntity<?> getAllClasses(
            int page, int size,
            String keyword,
            String sortType,
            String sortValue
    );

    @Operation(summary = "Create a new class")
    ResponseEntity<?> createClass(ClassRequest classModel);

    @Operation(summary = "Update a class")
    ResponseEntity<?> updateClass(Long id, ClassRequest classModel);

    @Operation(summary = "Delete a class")
    ResponseEntity<?> deleteClass(Long id);

    @Operation(summary = "Delete all class")
    ResponseEntity<?> deleteAllClasses();
}
