package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhone;
    private String userCode;
    private String imageKey;

    public static StudentResponse fromEntity(UserEntity userEntity) {
        return StudentResponse.builder()
                .studentId(userEntity.getUserId())
                .studentName(userEntity.getFullName())
                .studentEmail(userEntity.getEmail())
                .studentPhone(userEntity.getPhoneNumber())
                .userCode(userEntity.getUserCode())
                .imageKey(userEntity.getImageKey())
                .build();
    }
}
