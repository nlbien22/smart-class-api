package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.domain.entity.AnswerEntity;
import com.capstone.smartclassapi.domain.repository.AnswerRepository;
import com.capstone.smartclassapi.domain.service.interfaces.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService {
    final AnswerRepository answerRepository;
    @Override
    public List<AnswerEntity> getAnswers(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Override
    public AnswerEntity getAnswer(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow();
    }


}
