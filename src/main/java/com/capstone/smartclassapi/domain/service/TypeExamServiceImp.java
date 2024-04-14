package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.domain.entity.TypeExamEntity;
import com.capstone.smartclassapi.domain.repository.TypeExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeExamServiceImp {
    private final TypeExamRepository typeExamRepository;

    public TypeExamEntity getTypeExam(Long typeExamId) {
        return typeExamRepository.findById(typeExamId)
                .orElseThrow(() -> new RuntimeException("Type exam not found"));
    }
}
