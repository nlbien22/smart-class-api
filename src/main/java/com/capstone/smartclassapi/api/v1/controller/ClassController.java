package com.capstone.smartclassapi.api.v1.controller;


import com.capstone.smartclassapi.api.dto.request.ClassRequest;
import com.capstone.smartclassapi.api.openapi.controller.ClassControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController implements ClassControllerOpenApi {
    private final ClassService classService;
    private static final String DEFAULT_SORT_VALUE = "created_at";

    @GetMapping("/{id}")
    public ResponseEntity<?> getClass(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(classService.getClass(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllClasses(
            @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
            @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String sortType,
            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(classService.getAllClasses(page, size, keyword, sortType, sortValue));
    }

    @PostMapping("")
    public ResponseEntity<?> createClass(@Valid @RequestBody ClassRequest classModel) {
        classService.createClass(classModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable Long id,@Valid @RequestBody ClassRequest classModel) {
        classService.updateClass(id, classModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllClasses() {
        classService.deleteAllClasses();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
