package com.capstone.smartclassapi.domain.service;

import ch.qos.logback.core.joran.sanity.Pair;
import com.capstone.smartclassapi.api.dto.request.GenerateRequest;
import com.capstone.smartclassapi.api.dto.response.AnswerResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionAnswerResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionResponse;
import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import com.capstone.smartclassapi.domain.entity.enums.Level;
import com.capstone.smartclassapi.domain.exception.ResourceInvalidException;
import com.capstone.smartclassapi.domain.service.interfaces.GenerateService;
import com.capstone.smartclassapi.domain.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenerateServiceImp implements GenerateService {
    final QuestionService questionService;
    @Override
    public QuestionAnswerResponse generateQuestion(GenerateRequest generateRequest) {
        List<Level> levels = List.of(Level.EASY, Level.MEDIUM, Level.HARD, Level.VERY_HARD);
        int totalQuestionsNeeded = generateRequest.getLevel().stream().mapToInt(Long::intValue).sum();

        // Get all questions from the cloIds
        List<QuestionResponse> listQuestions = new ArrayList<>();
        for (Long i : generateRequest.getCloIds()) {
            List<QuestionResponse> temp = questionService.getQuestions(i, 0, 10, "", "", "").getQuestions();
            listQuestions.addAll(temp);
        }

        // Check if there are enough questions
        if (listQuestions.size() < totalQuestionsNeeded) {
            throw new ResourceInvalidException("Not enough questions");
        }

        List<Long> numQuestionsOfLevel = new ArrayList<>(generateRequest.getLevel());
        for (int i = 0; i < generateRequest.getLevel().size(); i++) {
            int finalI = i;
            int sizeOfThisLevel = listQuestions.stream().filter(q -> q.getLevel() == levels.get(finalI)).toList().size();
            numQuestionsOfLevel.set(i, Math.min(sizeOfThisLevel, generateRequest.getLevel().get(i)));
        }

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            int sizeOfThisLevel = listQuestions.stream().filter(q -> q.getLevel() == levels.get(finalI)).toList().size();
            if (numQuestionsOfLevel.stream().map(Long::intValue).mapToInt(Integer::intValue).sum() == totalQuestionsNeeded) {
                break;
            }
            int remain = totalQuestionsNeeded - numQuestionsOfLevel.stream().map(Long::intValue).mapToInt(Integer::intValue).sum();

            if(sizeOfThisLevel - numQuestionsOfLevel.get(i) <= remain) {
                numQuestionsOfLevel.set(i, (long) sizeOfThisLevel);
            } else {
                numQuestionsOfLevel.set(i, (numQuestionsOfLevel.get(i) + remain));
            }
        }

        List<QuestionResponse> randomQuestions = new ArrayList<>();
        int i = 0;
        while (i < 4){
            List<QuestionResponse> temp = getQuestionsByLevel(listQuestions, levels.get(i), numQuestionsOfLevel.get(i));
            randomQuestions.addAll(temp);
            i++;

        }

        Collections.shuffle(randomQuestions);
        randomQuestions.forEach(this::shuffleAnswers);

        return QuestionAnswerResponse.builder().questions(randomQuestions).build();
    }

    private List<QuestionResponse> getQuestionsByLevel(List<QuestionResponse> listQuestions, Level level, Long numQuestions) {
        List<QuestionResponse> questionsByLevel = new ArrayList<>();
        for (QuestionResponse question : listQuestions) {
            if (question.getLevel() == level) {
                questionsByLevel.add(question);
            }
        }
        Collections.shuffle(questionsByLevel);
        return questionsByLevel.stream()
                .limit(numQuestions)
                .collect(Collectors.toList());
    }

    private void shuffleAnswers(QuestionResponse question) {
        List<AnswerResponse> shuffledAnswers = new ArrayList<>(question.getAnswers());
        Collections.shuffle(shuffledAnswers);
        question.setAnswers(shuffledAnswers);
    }

}
