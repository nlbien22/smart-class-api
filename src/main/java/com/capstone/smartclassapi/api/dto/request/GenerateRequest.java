package com.capstone.smartclassapi.api.dto.request;

import io.swagger.models.auth.In;
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
public class GenerateRequest {
    @Schema(description = "Subject id", example = "2")
    private Long subjectId;
    @Schema(description = "List of clo ids", example = "[4, 5, 10]")
    private List<Long> cloIds;
    @Schema(description = "Level", example = "[1, 0, 0, 0]")
    private List<Long> level;
}
