package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.QuestionAnswerRequest;
import com.capstone.smartclassapi.api.dto.request.QuestionRequest;
import com.capstone.smartclassapi.api.dto.response.QuestionAnswerResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionResponse;
import com.capstone.smartclassapi.domain.entity.AnswerEntity;
import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import com.capstone.smartclassapi.domain.exception.ResourceInvalidException;
import com.capstone.smartclassapi.domain.repository.QuestionRepository;
import com.capstone.smartclassapi.domain.service.interfaces.CloService;
import com.capstone.smartclassapi.domain.service.interfaces.ImageService;
import com.capstone.smartclassapi.domain.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionRepository questionRepository;
    private final CloService cloService;
    private final ImageService imageService;

    @Override
    public QuestionResponse getQuestion(Long cloId, Long questionId) {
        return null;
    }

    @Override
    public QuestionAnswerResponse getQuestions(Long cloId) {
        return null;
    }

    @Override
    public void createQuestion(Long cloId, QuestionRequest request, MultipartFile multipartFile) {
        String imageKey = "";
        try {
            imageKey = imageService.save(multipartFile, "questions/");
        } catch (Exception e) {
            throw new ResourceInvalidException("Failed to save image");
        }
        QuestionEntity questionEntity = QuestionEntity.builder()
                .questionContent(request.getQuestionContent())
                .questionImage(imageKey)
                .level(request.getLevel())
                .answers(new ArrayList<>())
                .clo(cloService.getCloById(cloId))
                .build();
        request.getAnswers().stream().toList().forEach(answer -> {
            AnswerEntity answerEntity = AnswerEntity.builder()
                    .answerContent(answer.getAnswerContent())
                    .isCorrect(answer.isCorrect())
                    .question(questionEntity)
                    .build();
            questionEntity.getAnswers().add(answerEntity);
        });
        questionRepository.save(questionEntity);
    }

    @Override
    public void createListQuestion(Long cloId, QuestionAnswerRequest request) {
        cloService.getCloById(cloId);

        for (QuestionRequest question : request.getQuestions()) {
            QuestionEntity questionEntity = QuestionEntity.builder()
                    .questionContent(question.getQuestionContent())
//                    .questionImage(question.getQuestionImage())
                    .level(question.getLevel())
                    .answers(new ArrayList<>())
                    .clo(cloService.getCloById(cloId))
                    .build();
            question.getAnswers().stream().toList().forEach(answer -> {
                AnswerEntity answerEntity = AnswerEntity.builder()
                        .answerContent(answer.getAnswerContent())
                        .isCorrect(answer.isCorrect())
                        .question(questionEntity)
                        .build();
                questionEntity.getAnswers().add(answerEntity);
            });
            questionRepository.save(questionEntity);
        }
    }

    @Override
    public void updateQuestion(Long cloId, Long questionId, QuestionAnswerRequest request) {

    }

    @Override
    public void deleteQuestion(Long cloId, Long questionId) {

    }
}
