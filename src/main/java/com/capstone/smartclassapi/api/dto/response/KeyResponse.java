package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.KeyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyResponse {
    private Long keyId;
    private Long keyCode;

    public static KeyResponse fromEntity(KeyEntity keyEntity) {
        return KeyResponse.builder()
                .keyId(keyEntity.getKeyId())
                .keyCode(keyEntity.getKeyCode())
                .build();
    }
}
