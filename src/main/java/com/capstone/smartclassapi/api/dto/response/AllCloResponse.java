package com.capstone.smartclassapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllCloResponse {
    private List<CloResponse> clos;
    private Long totalClos;
}
