package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.ExamEntity;
import com.capstone.smartclassapi.domain.entity.TypeExamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {
    private Long examId;
    private String examName;
    private Date examDate;
    private Integer pointExam;
    private Integer numExam;
    private TypeExamResponse typeExam;

    public static ExamResponse fromEntity(ExamEntity examEntity) {
        return ExamResponse.builder()
                .examId(examEntity.getExamId())
                .examName(examEntity.getExamName())
                .examDate(examEntity.getExamDate())
                .pointExam(examEntity.getPointExam())
                .numExam(examEntity.getNumExam())
                .typeExam(TypeExamResponse.builder()
                        .typeExamId(examEntity.getTypeExam().getTypeExamId())
                        .typeExamName(examEntity.getTypeExam().getTypeExamName())
                        .build())
                .build();
    }
}
