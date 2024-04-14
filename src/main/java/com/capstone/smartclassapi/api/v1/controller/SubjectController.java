package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.SubjectRequest;
import com.capstone.smartclassapi.api.dto.response.SubjectResponse;
import com.capstone.smartclassapi.api.openapi.controller.SubjectControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController implements SubjectControllerOpenApi {
    private final SubjectService subjectService;
    private static final String DEFAULT_SORT_VALUE = "subject_name";

    @GetMapping("/{subjectId}")
    @Override
    public ResponseEntity<?> getSubject(@PathVariable Long subjectId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SubjectResponse.fromEntity(subjectService.getSubject(subjectId)));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllSubjects(@RequestParam(defaultValue = DefaultListParams.PAGE) int page,
                                            @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
                                            @RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(defaultValue = "desc") String sortType,
                                            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subjectService.getAllSubjects(page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest request) {
        subjectService.createSubject(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    @Override
    public ResponseEntity<?> updateSubject(@PathVariable Long subjectId, @RequestBody SubjectRequest request) {
        subjectService.updateSubject(subjectId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{subjectId}")
    @Override
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
