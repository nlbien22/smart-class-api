package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.QuestionAnswerRequest;
import com.capstone.smartclassapi.api.dto.request.QuestionRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionControllerOpenApi {
    @Operation(summary = "Get a question by clo and question id")
    ResponseEntity<?> getQuestion(Long cloId, Long questionId);
    @Operation(summary = "Get all questions by clo")
    ResponseEntity<?> getAllQuestions(Long cloId);
    @Operation(summary = "Create a question by clo")
    ResponseEntity<?> createQuestionAnswer(Long cloId, QuestionRequest request, MultipartFile multipartFile);
    @Operation(summary = "Create a list of questions by clo")
    ResponseEntity<?> createListQuestionsAnswers(Long cloId, QuestionAnswerRequest request);
    @Operation(summary = "Update a question by clo and question id")
    ResponseEntity<?> updateQuestion(Long cloId, Long questionId, QuestionAnswerRequest request);
    @Operation(summary = "Delete a question by clo and question id")
    ResponseEntity<?> deleteQuestion(Long cloId, Long questionId);
}
