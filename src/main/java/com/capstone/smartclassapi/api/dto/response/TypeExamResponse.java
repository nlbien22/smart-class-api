package com.capstone.smartclassapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeExamResponse {
    private Long typeExamId;
    private String typeExamName;
}
