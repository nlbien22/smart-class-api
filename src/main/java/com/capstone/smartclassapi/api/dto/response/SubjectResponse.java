package com.capstone.smartclassapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
    private Long subjectId;
    private String subjectName;

    public static SubjectResponse fromEntity(com.capstone.smartclassapi.domain.entity.SubjectEntity subjectEntity) {
        return SubjectResponse.builder()
                .subjectId(subjectEntity.getSubjectId())
                .subjectName(subjectEntity.getSubjectName())
                .build();
    }

    public static com.capstone.smartclassapi.domain.entity.SubjectEntity toEntity(SubjectResponse subjectEntity) {
        return com.capstone.smartclassapi.domain.entity.SubjectEntity.builder()
                .subjectId(subjectEntity.getSubjectId())
                .subjectName(subjectEntity.getSubjectName())
                .build();
    }
}
