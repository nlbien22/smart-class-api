package com.capstone.smartclassapi.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoQuestionRequest {
    private Long questionId;
    private Long questionIndex;
    private String correctAnswer;
}