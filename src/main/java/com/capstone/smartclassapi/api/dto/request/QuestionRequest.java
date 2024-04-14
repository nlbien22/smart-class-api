package com.capstone.smartclassapi.api.dto.request;

import com.capstone.smartclassapi.domain.entity.enums.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    @Schema(description = "Question content", example = "What is the capital of France?")
    private String questionContent;
//    @Schema(description = "Question image", example = "https://www.google.com/image.jpg")
//    private String questionImage;
    @Schema(description = "Question level", example = "EASY")
    private Level level;
    private List<AnswerRequest> answers;
}
