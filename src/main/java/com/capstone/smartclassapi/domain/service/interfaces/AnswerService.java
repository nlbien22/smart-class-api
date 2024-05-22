package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.domain.entity.AnswerEntity;

import java.util.List;

public interface AnswerService {
    List<AnswerEntity> getAnswers(Long questionId);
    AnswerEntity getAnswer(Long answerId);
}
