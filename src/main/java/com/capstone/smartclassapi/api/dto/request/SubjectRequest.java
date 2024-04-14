package com.capstone.smartclassapi.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequest {
    @Schema(description = "Subject name", example = "Toán Cao Cấp")
    private String subjectName;
}
