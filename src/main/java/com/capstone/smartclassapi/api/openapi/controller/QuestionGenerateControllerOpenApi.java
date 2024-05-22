package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.GenerateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface QuestionGenerateControllerOpenApi {
    @Operation(summary = "Generate question")
    ResponseEntity<?> generateQuestion(GenerateRequest generateRequest);
}
