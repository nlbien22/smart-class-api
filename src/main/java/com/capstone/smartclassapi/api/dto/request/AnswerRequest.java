package com.capstone.smartclassapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class AnswerRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Answer id", example = "1")
    private Long answerId;
    @Schema(description = "Answer content", example = "Paris")
    private String answerContent;
    @Schema(description = "Is the answer correct", example = "true")
    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
