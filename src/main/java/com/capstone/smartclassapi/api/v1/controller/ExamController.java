package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.openapi.controller.ExamControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController implements ExamControllerOpenApi {
    private static final String DEFAULT_SORT_VALUE = "exam_date";

    private final ExamService examService;

    @GetMapping("/{examId}")
    @Override
    public ResponseEntity<?> getExam(@PathVariable Long examId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(examService.getExam(examId));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllExams(
            @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
            @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String sortType,
            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(examService.getAllExams(page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createExam(@RequestBody ExamRequest exam) {
        examService.createExam(exam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{examId}")
    @Override
    public ResponseEntity<?> updateExam(@PathVariable Long examId, @RequestBody ExamRequest exam) {
        examService.updateExam(examId, exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{examId}")
    @Override
    public ResponseEntity<?> deleteExam(@PathVariable Long examId) {
        examService.deleteExam(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
