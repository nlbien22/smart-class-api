package com.capstone.smartclassapi.api.dto.response;

import com.capstone.smartclassapi.domain.entity.ClassEntity;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllClassResponse {
    List<ClassResponse> listClasses;
    private Long totalClasses;

}
