package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.QuestionAnswerRequest;
import com.capstone.smartclassapi.api.dto.request.QuestionRequest;
import com.capstone.smartclassapi.api.dto.response.QuestionAnswerResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionService {
    QuestionResponse getQuestion(Long cloId, Long questionId);
    QuestionAnswerResponse getQuestions(Long cloId);
    void createQuestion(Long cloId, QuestionRequest request, MultipartFile multipartFile);
    void createListQuestion(Long cloId, QuestionAnswerRequest request);
    void updateQuestion(Long cloId, Long questionId, QuestionAnswerRequest request);
    void deleteQuestion(Long cloId, Long questionId);
}
