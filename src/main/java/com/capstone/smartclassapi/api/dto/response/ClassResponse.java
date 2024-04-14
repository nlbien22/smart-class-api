package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.api.dto.request.ClassRequest;
import com.capstone.smartclassapi.domain.entity.ClassEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponse {
    private Long classId;
    private String className;
    private LocalDateTime createdAt;

    public static ClassResponse fromEntity(ClassEntity classEntity) {
        return ClassResponse.builder()
                .classId(classEntity.getClassId())
                .className(classEntity.getClassName())
                .createdAt(classEntity.getCreatedAt())
                .build();
    }

    public static ClassEntity toEntity(ClassResponse classResponse) {
        return ClassEntity.builder()
                .classId(classResponse.getClassId())
                .className(classResponse.getClassName())
                .createdAt(classResponse.getCreatedAt())
                .build();
    }
}
