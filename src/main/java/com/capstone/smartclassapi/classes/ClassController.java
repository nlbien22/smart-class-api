package com.capstone.smartclassapi.classes;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.capstone.smartclassapi.constants.DefaultListParams.PAGE;
import static com.capstone.smartclassapi.constants.DefaultListParams.SIZE;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    private static final String DEFAULT_SORT_VALUE = "created_at";

    @GetMapping("/{id}")
    public ResponseEntity<?> getClass(@PathVariable Long id) {
        return ResponseEntity.status(200).body(classService.getClass(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllClasses(
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String sortType,
            @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue
    ) {
        return ResponseEntity.status(200).body(classService.getAllClasses(page, size, keyword, sortType, sortValue));
    }

    @PostMapping("")
    public ResponseEntity<?> createClass(@Valid @RequestBody Class classModel) {
        return ResponseEntity.status(201).body(classService.createClass(classModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable Long id,@Valid @RequestBody Class classModel) {
        return ResponseEntity.status(200).body(classService.updateClass(id, classModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        return ResponseEntity.status(200).body(classService.deleteClass(id));
    }


}
