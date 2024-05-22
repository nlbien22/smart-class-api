package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.KeyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyResponse {
    private String keyId;
    private String keyCode;
    private List<Integer> type;
    private List<Integer> answers;
    private List<int[]> answer;
    public static KeyResponse fromEntity(KeyEntity keyEntity) {
        return KeyResponse.builder()
                .keyId(keyEntity.getKeyId().toString())
                .keyCode(keyEntity.getKeyCode().toString())

                .build();
    }
}
