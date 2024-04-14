package com.capstone.smartclassapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllKeysResponse {
    List<KeyResponse> listKeys;
    private Long totalKeys;
}
