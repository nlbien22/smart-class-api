package com.capstone.smartclassapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradedResponse {
    Long gradedId;
    Long examId;
    String keyCode;
    String imageKey;
    List<Integer> answers;
    int correct;
    int total;
    Float score;
    String className;
    String userCode;
    String studentName;
    String nameKey;
    int isError;
    LocalDateTime updatedAt;
}
