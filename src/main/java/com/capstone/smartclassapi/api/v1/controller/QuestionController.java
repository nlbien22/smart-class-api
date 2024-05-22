package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.QuestionAnswerRequest;
import com.capstone.smartclassapi.api.dto.request.QuestionRequest;
import com.capstone.smartclassapi.api.openapi.controller.QuestionControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.service.interfaces.ImageService;
import com.capstone.smartclassapi.domain.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clo/{cloId}/question")
@RequiredArgsConstructor
public class QuestionController implements QuestionControllerOpenApi {
    private final QuestionService questionService;
    private final ImageService imageService;
    private static final String DEFAULT_SORT_VALUE = "exam_date";

    @GetMapping("/{questionId}")
    @Override
    public ResponseEntity<?> getQuestion(@PathVariable Long cloId,@PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestion(cloId, questionId));
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAllQuestions(@PathVariable Long cloId,
                                             @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
                                             @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
                                             @RequestParam(defaultValue = "") String keyword,
                                             @RequestParam(defaultValue = "") String sortType,
                                             @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestions(cloId, page, size, keyword, sortType, sortValue));
    }

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Override
    public ResponseEntity<?> createQuestionAnswer(@PathVariable Long cloId,
                                                  @ModelAttribute QuestionRequest request,
                                                  @RequestPart("questionImage") MultipartFile multipartFile) {
        questionService.createQuestion(cloId, request, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/list")
    @Override
    public ResponseEntity<?> createListQuestionsAnswers(@PathVariable Long cloId, @RequestBody QuestionAnswerRequest request) {
        questionService.createListQuestion(cloId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{questionId}")
    @Override
    public ResponseEntity<?> updateQuestion(@PathVariable Long cloId, @PathVariable Long questionId, @RequestBody QuestionAnswerRequest request) {
        questionService.updateQuestion(cloId, questionId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{questionId}")
    @Override
    public ResponseEntity<?> deleteQuestion(@PathVariable Long cloId, @PathVariable Long questionId) {
        questionService.deleteQuestion(cloId, questionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
