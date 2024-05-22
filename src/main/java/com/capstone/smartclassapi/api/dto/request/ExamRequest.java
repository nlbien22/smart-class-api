package com.capstone.smartclassapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamRequest {
    @Schema(description = "Exam name", example = "Midterm Exam")
    private String examName;
    @Schema(description = "Exam date", example = "2021-10-10")
    private Date examDate;
    @Schema(description = "Exam point", example = "100")
    private Float pointExam;
    @Schema(description = "Number of questions", example = "10")
    private Integer numExam;
    @Schema(description = "Type of exam id", example = "1")
    private Long typeExamId;
}
