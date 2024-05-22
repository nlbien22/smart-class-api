package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.QuestionAnswerRequest;
import com.capstone.smartclassapi.api.dto.request.QuestionRequest;
import com.capstone.smartclassapi.api.dto.response.*;
import com.capstone.smartclassapi.domain.entity.AnswerEntity;
import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.exception.ResourceInvalidException;
import com.capstone.smartclassapi.domain.repository.QuestionRepository;
import com.capstone.smartclassapi.domain.service.interfaces.AnswerService;
import com.capstone.smartclassapi.domain.service.interfaces.CloService;
import com.capstone.smartclassapi.domain.service.interfaces.ImageService;
import com.capstone.smartclassapi.domain.service.interfaces.QuestionService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final CloService cloService;
    private final ImageService imageService;

    @Override
    public QuestionResponse getQuestion(Long cloId, Long questionId) {
        cloService.getCloById(cloId);
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceInvalidException("Question not found"));
        List<AnswerEntity> answers = answerService.getAnswers(questionId);
        return QuestionResponse.builder()
                .questionId(questionEntity.getQuestionId())
                .questionContent(questionEntity.getQuestionContent())
                .questionImage(questionEntity.getQuestionImage())
                .level(questionEntity.getLevel())
                .answers(answers.stream().map(AnswerResponse::fromEntity).toList())
                .build();
    }

    @Override
    public QuestionAnswerResponse getQuestions(Long cloId, int page, int size, String keyword, String sortType, String sortValue) {
//        CommonValidation.validatePageAndSize(page, size);
//
//        Sort sorting = Sorting.getSorting(sortType, sortValue);
//        Pageable pageable = PageRequest.of(page, size, sorting);
//        List<UserEntity> listStudents = studentRepository.findAllStudents(
//                classId,
//                CommonValidation.escapeSpecialCharacters(keyword.trim()),
//                pageable);
//
//        return AllStudentResponse.builder()
//                .listStudents(listStudents.stream().map(StudentResponse::fromEntity).toList())
//                .totalStudents(studentRepository.countAllStudents(
//                        classId,
//                        CommonValidation.escapeSpecialCharacters(keyword.trim())))
//                .build();
        CommonValidation.validatePageAndSize(page, size);
        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<QuestionEntity> listQuestions = questionRepository.findAllQuestionsByCloId(cloId);
        return QuestionAnswerResponse.builder()
                .questions(listQuestions.stream().map(QuestionResponse::fromEntity).toList())
                .build();

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

    @Override
    public QuestionEntity getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceInvalidException("Question not found"));
    }
}
