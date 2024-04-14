package com.capstone.smartclassapi.api.dto.request;

import com.capstone.smartclassapi.domain.entity.enums.TypeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyRequest {
    private Long keyCode;
    private TypeKey typeKey;
    private List<AutoQuestionRequest> autoQuestions;
}
