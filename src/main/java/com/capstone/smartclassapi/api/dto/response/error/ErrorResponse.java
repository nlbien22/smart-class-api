package com.capstone.smartclassapi.api.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;

    private String message;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}