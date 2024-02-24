package com.capstone.smartclassapi.classes;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClass(@PathVariable Long id) {
        return ResponseEntity.status(200).body(classService.getClass(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.status(200).body(classService.getAllClasses());
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
