package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.KeyRequest;
import com.capstone.smartclassapi.api.dto.response.AllKeysResponse;
import com.capstone.smartclassapi.api.dto.response.AnswerResponse;
import com.capstone.smartclassapi.api.dto.response.KeyResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionResponse;
import com.capstone.smartclassapi.domain.entity.*;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.KeyRepository;
import com.capstone.smartclassapi.domain.service.interfaces.AnswerService;
import com.capstone.smartclassapi.domain.service.interfaces.ExamService;
import com.capstone.smartclassapi.domain.service.interfaces.KeyService;
import com.capstone.smartclassapi.domain.service.interfaces.QuestionService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class KeyServiceImp implements KeyService {
    private final KeyRepository keyRepository;
    private final ExamService examService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    public KeyEntity getKey(Long examId, Long keyId) {
        return keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
    }

    @Override
    public AllKeysResponse getAllKeys(Long examId, int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<KeyEntity> listKeys = keyRepository.findAllKeysByExamId(
                examId,
                pageable
        );

        List<KeyResponse> keyResponses = new ArrayList<>();
        for (KeyEntity keyEntity : listKeys) {
            List<Integer> listAnswers = convertAnswerToInt(keyEntity.getAutoQuestions().stream().map(AutoQuestionEntity::getCorrectAnswer).toList());
            List<Integer> fullZeroList = Collections.nCopies(listAnswers.size(), 0);
            List<int[]> answer = IntStream.range(0, listAnswers.size())
                    .mapToObj(i -> new int[]{listAnswers.get(i), fullZeroList.get(i)})
                    .toList();
            keyResponses.add(KeyResponse.builder()
                    .keyId(keyEntity.getKeyId().toString())
                    .keyCode(keyEntity.getKeyCode().toString())
                    .type(fullZeroList)
                    .answers(listAnswers)
                    .answer(answer)
                    .build());
        }

        return AllKeysResponse.builder()
                .listKeys(keyResponses)
                .totalKeys(keyRepository.countKeyByExamId(examId))
                .build();
    }

    @Transactional
    @Override
    public void createKey(Long examId, KeyRequest request) {
        keyRepository.findByKeyCode(examId, request.getKeyCode())
                .ifPresent(keyEntity -> {
                    throw new ResourceNotFoundException("Key already exists");
                });

        ExamEntity examEntity = examService.getExamEntity(examId);
        KeyEntity keyEntity = KeyEntity.builder()
                .keyCode(request.getKeyCode())
                .exams(List.of(examEntity))
                .typeKey(request.getTypeKey())
                .autoQuestions(new ArrayList<>())
                .gradedEntities(new ArrayList<>())
                .build();
        List<String> correctAnswers = getCorrectAnswerIndexes(request.getQuestions());
        System.out.println(correctAnswers);
        for (int i = 0; i < request.getQuestions().size(); i++) {
            QuestionResponse question = request.getQuestions().get(i);

            AutoQuestionEntity autoQuestionEntity = AutoQuestionEntity.builder()
                    .questionIndex((long) i+1)
                    .correctAnswer(correctAnswers.get(i))
                    .autoAnswers(new ArrayList<>())
                    .question(questionService.getQuestionById(question.getQuestionId()))
                    .key(keyEntity)
                    .build();
            for (int j = 0; j < question.getAnswers().size(); j++) {
                AnswerEntity answerEntity = answerService.getAnswer(question.getAnswers().get(j).getAnswerId());
                AutoAnswerEntity autoAnswerEntity = AutoAnswerEntity.builder()
                        .answer(answerEntity)
                        .autoQuestion(autoQuestionEntity)
                        .sequenceOrder((long) (j + 1))
                        .build();
                autoQuestionEntity.getAutoAnswers().add(autoAnswerEntity);
            }
            keyEntity.getAutoQuestions().add(autoQuestionEntity);
        }
        keyRepository.save(keyEntity);
    }

    @Override
    public void updateKey(Long examId, Long keyId, KeyRequest request) {
        KeyEntity keyEntity = keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        Optional<KeyEntity> keyEntityOptional = keyRepository
                .findByKeyCode(examId, request.getKeyCode());
        if (keyEntityOptional.isPresent() && !keyEntityOptional.get().getKeyCode().equals(request.getKeyCode())) {
            throw new ResourceConflictException("Class name already exists");
        }

        keyEntity.setKeyCode(request.getKeyCode());
        keyRepository.save(keyEntity);
    }

    @Override
    public void deleteKey(Long examId, Long keyId) {
        KeyEntity keyEntity = keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        keyRepository.delete(keyEntity);
    }

    @Override
    public KeyResponse getKeyResponse(Long examId, Long keyId) {
        KeyEntity keyEntity = getKey(examId, keyId);
        List<Integer> listAnswers = convertAnswerToInt(keyEntity.getAutoQuestions().stream().map(AutoQuestionEntity::getCorrectAnswer).toList());
        List<Integer> fullZeroList = Collections.nCopies(listAnswers.size(), 0);
        List<int[]> answer = IntStream.range(0, listAnswers.size())
                .mapToObj(i -> new int[]{listAnswers.get(i), fullZeroList.get(i)})
                .toList();
        return KeyResponse.builder()
                .keyId(keyEntity.getKeyId().toString())
                .keyCode(keyEntity.getKeyCode().toString())
                .type(fullZeroList)
                .answers(listAnswers)
                .answer(answer)
                .build();
    }

    @Override
    public KeyEntity findByKeyCode(Long examId, String keyCode) {
        return keyRepository.findByKeyCode(examId, keyCode).orElse(null);
    }

    List<String> getCorrectAnswerIndexes(List<QuestionResponse> questions) {
        List<String> correctAnswerIndexes = new ArrayList<>();
        String[] alphabet = new String[]{"A", "B", "C", "D"};
        for (QuestionResponse question : questions) {
            int correctAnswerIndex = -1;
            List<AnswerResponse> answers = question.getAnswers();

            for (int i = 0; i < answers.size(); i++) {
                AnswerResponse answer = answers.get(i);
                if (answer.isCorrect()) {
                    correctAnswerIndex = i;
                    break;
                }
            }

            correctAnswerIndexes.add(alphabet[correctAnswerIndex]);
        }

        return correctAnswerIndexes;
    }

    List<Integer> convertAnswerToInt(List<String> answers) {
        List<Integer> listAnswers = new ArrayList<>();
        for (String answer : answers) {
            listAnswers.add(answer.charAt(0) - 'A');
        }
        return listAnswers;
    }
}
