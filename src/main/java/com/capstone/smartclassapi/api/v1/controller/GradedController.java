package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.response.GradedResponse;
import com.capstone.smartclassapi.api.openapi.controller.GradedControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.GradedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam/{examId}/graded")
@RequiredArgsConstructor
public class GradedController implements GradedControllerOpenApi {
    private final GradedService gradedService;
    final String DEFAULT_SORT_VALUE = "graded_date";
    @GetMapping("/{gradedId}")
    @Override
    public ResponseEntity<?> getGraded(@PathVariable Long examId,@PathVariable Long gradedId) {
        return ResponseEntity.ok(gradedService.getGraded(examId, gradedId));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllGrades(@PathVariable Long examId,
                                          @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
                                          @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
                                          @RequestParam(defaultValue = "") String keyword,
                                          @RequestParam(defaultValue = "") String sortType,
                                          @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue) {
        return ResponseEntity.ok(gradedService.getAllGrades(examId, page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createGraded(@PathVariable Long examId,@RequestBody GradedResponse request) {
        gradedService.saveGraded(examId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{gradedId}")
    @Override
    public ResponseEntity<?> updateGraded(@PathVariable Long examId, @PathVariable Long gradedId, @RequestBody GradedResponse request) {
        gradedService.updateGraded(examId, gradedId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{gradedId}")
    @Override
    public ResponseEntity<?> deleteGraded(@PathVariable Long examId, @PathVariable Long gradedId) {
        gradedService.deleteGraded(examId, gradedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteAllGraded(@PathVariable Long examId) {
        gradedService.deleteAllGraded(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
