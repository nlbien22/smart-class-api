package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.openapi.controller.ExamInClassControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes/{classId}/exam")
@RequiredArgsConstructor
public class ExamInClassController implements ExamInClassControllerOpenApi {
    private static final String DEFAULT_SORT_VALUE = "exam_date";

    private final ExamService examService;

    @GetMapping("/{examId}")
    @Override
    public ResponseEntity<?> getExamsOfClass(@RequestParam Long classId, @RequestParam Long examId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(examService.getExamsOfClass(classId, examId));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllExamsOfClass(
            @PathVariable Long classId,
            @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
            @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String sortType,
            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(examService.getAllExamsOfClass(classId, page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createExamOfClass(@PathVariable Long classId, @RequestBody ExamRequest exam) {
        examService.createExamOfClass(classId, exam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{examId}")
    @Override
    public ResponseEntity<?> updateExamOfClass(@PathVariable Long classId, @PathVariable Long examId, @RequestBody ExamRequest exam) {
        examService.updateExamOfClass(classId, examId, exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{examId}")
    @Override
    public ResponseEntity<?> deleteExamOfClass(@PathVariable Long classId, @PathVariable Long examId) {
        examService.deleteExamOfClass(classId, examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteAllExamsOfClass(@PathVariable Long classId) {
        examService.deleteAllExamsOfClass(classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
