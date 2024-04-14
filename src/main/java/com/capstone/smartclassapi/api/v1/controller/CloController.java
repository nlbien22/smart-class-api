package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.CloRequest;
import com.capstone.smartclassapi.api.dto.response.CloResponse;
import com.capstone.smartclassapi.api.openapi.controller.CloControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.CloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subject/{subjectId}/clo")
@RequiredArgsConstructor
public class CloController implements CloControllerOpenApi {
    private final CloService cloService;
    private static final String DEFAULT_SORT_VALUE = "created_at";

    @GetMapping("/{cloId}")
    @Override
    public ResponseEntity<?> getClo(@PathVariable Long subjectId, @PathVariable Long cloId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CloResponse.fromEntity(cloService.getClo(subjectId, cloId)));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getClos(
            @PathVariable Long subjectId,
            @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
            @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "desc") String sortType,
            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cloService.getAllClos(subjectId, page, size, keyword, sortType, sortValue));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createClo(@PathVariable Long subjectId, @RequestBody CloRequest request) {
        cloService.createClo(subjectId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{cloId}")
    @Override
    public ResponseEntity<?> updateClo(@PathVariable Long subjectId, @PathVariable Long cloId, @RequestBody CloRequest request) {
        cloService.updateClo(subjectId, cloId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{cloId}")
    @Override
    public ResponseEntity<?> deleteClo(@PathVariable Long subjectId, @PathVariable Long cloId) {
        cloService.deleteClo(subjectId, cloId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
