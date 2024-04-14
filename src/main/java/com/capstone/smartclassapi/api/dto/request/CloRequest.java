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
public class CloRequest {
    @Schema(description = "Clo title", example = "Chương 1")
    private String cloTitle;
}
