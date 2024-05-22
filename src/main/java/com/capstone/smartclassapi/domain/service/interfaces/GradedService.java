package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.response.AllGradedResponse;
import com.capstone.smartclassapi.api.dto.response.GradedResponse;

import java.util.List;

public interface GradedService {
    void saveGraded(Long examId, GradedResponse request);
    void updateGraded(Long examId, Long gradedId, GradedResponse request);
    void deleteGraded(Long examId, Long gradedId);
    void deleteAllGraded(Long examId);
    GradedResponse getGraded(Long examId, Long gradedId);
    AllGradedResponse getAllGrades(Long examId, int page, int size, String keyword, String sortType, String sortValue);
}
