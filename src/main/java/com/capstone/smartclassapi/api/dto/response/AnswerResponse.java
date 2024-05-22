package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.AnswerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    private Long answerId;
    private String answerContent;
    @JsonProperty("isCorrect")
    private boolean isCorrect;

    public static AnswerResponse fromEntity(AnswerEntity answerEntity) {
        return AnswerResponse.builder()
                .answerId(answerEntity.getAnswerId())
                .answerContent(answerEntity.getAnswerContent())
                .isCorrect(answerEntity.isCorrect())
                .build();
    }

    public static AnswerEntity toEntity(AnswerResponse answerResponse) {
        return AnswerEntity.builder()
                .answerId(answerResponse.getAnswerId())
                .answerContent(answerResponse.getAnswerContent())
                .isCorrect(answerResponse.isCorrect())
                .build();
    }
}
