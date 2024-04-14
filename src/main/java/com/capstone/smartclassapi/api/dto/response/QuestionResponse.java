package com.capstone.smartclassapi.api.dto.response;

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
}
