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
    private Float pointExam;
    private Integer numExam;
    private Long typeExam;
    private Long classId;

    public static ExamResponse fromEntity(ExamEntity examEntity) {
        ExamResponse.ExamResponseBuilder builder = ExamResponse.builder()
                .examId(examEntity.getExamId())
                .examName(examEntity.getExamName())
                .examDate(examEntity.getExamDate())
                .pointExam(examEntity.getPointExam())
                .numExam(examEntity.getNumExam())
                .typeExam(examEntity.getTypeExam().getTypeExamId());

        if (examEntity.getClassEntity() != null) {
            builder.classId(examEntity.getClassEntity().getClassId());
        }

        return builder.build();
    }
}