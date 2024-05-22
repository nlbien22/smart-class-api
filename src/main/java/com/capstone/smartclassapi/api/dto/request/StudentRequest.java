package com.capstone.smartclassapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRequest {
    @Schema(description = "Student name", example = "John Doe")
    private String studentName;
    @Schema(description = "Student email", example = "johndoe@gmail.com")
    private String studentEmail;
    @Schema(description = "Student phone", example = "1234567890")
    private String studentPhone;
    @Schema(description = "User code", example = "123456")
    private String userCode;
    @Schema(description = "Image key", example = "123456")
    private String imageKey;
}
