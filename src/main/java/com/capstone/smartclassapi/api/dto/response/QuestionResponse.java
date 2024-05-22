package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import com.capstone.smartclassapi.domain.entity.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long questionId;
    private String questionContent;
    private String questionImage;
    private Level level;
    private List<AnswerResponse> answers;

    public static QuestionResponse fromEntity(QuestionEntity questionEntity) {
        return QuestionResponse.builder()
                .questionId(questionEntity.getQuestionId())
                .questionContent(questionEntity.getQuestionContent())
                .questionImage(questionEntity.getQuestionImage())
                .level(questionEntity.getLevel())
                .answers(questionEntity.getAnswers().stream().map(AnswerResponse::fromEntity).toList())
                .build();
    }
}
