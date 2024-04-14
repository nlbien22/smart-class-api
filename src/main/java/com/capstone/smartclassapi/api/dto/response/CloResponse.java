package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.CloEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloResponse {
    private Long cloId;
    private String cloTitle;

    public static CloResponse fromEntity(CloEntity CloEntity) {
        return CloResponse.builder()
                .cloId(CloEntity.getCloId())
                .cloTitle(CloEntity.getCloTitle())
                .build();
    }
}
