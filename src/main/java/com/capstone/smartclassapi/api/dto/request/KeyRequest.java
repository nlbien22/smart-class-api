package com.capstone.smartclassapi.api.dto.request;

import com.capstone.smartclassapi.api.dto.response.QuestionAnswerResponse;
import com.capstone.smartclassapi.api.dto.response.QuestionResponse;
import com.capstone.smartclassapi.domain.entity.enums.TypeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyRequest {
    private String keyCode;
    private TypeKey typeKey;
    private List<QuestionResponse> questions;
    private Map<Long, Long> manualQuestionAnswer;
}
