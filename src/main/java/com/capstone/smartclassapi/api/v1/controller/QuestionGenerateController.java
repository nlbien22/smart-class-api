package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.GenerateRequest;
import com.capstone.smartclassapi.api.openapi.controller.QuestionGenerateControllerOpenApi;
import com.capstone.smartclassapi.domain.service.interfaces.GenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class QuestionGenerateController implements QuestionGenerateControllerOpenApi {
    final GenerateService generateService;

    @PostMapping
    @Override
    public ResponseEntity<?> generateQuestion(@RequestBody GenerateRequest generateRequest) {
        return ResponseEntity.ok(generateService.generateQuestion(generateRequest));
    }
}
