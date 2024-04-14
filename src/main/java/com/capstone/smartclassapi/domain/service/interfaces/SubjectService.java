package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.SubjectRequest;
import com.capstone.smartclassapi.api.dto.response.AllSubjectsResponse;
import com.capstone.smartclassapi.domain.entity.SubjectEntity;

public interface SubjectService {
    SubjectEntity getSubject(Long subjectId);
    AllSubjectsResponse getAllSubjects(int page, int size, String keyword, String sortType, String sortValue);
    void createSubject(SubjectRequest request);
    void updateSubject(Long subjectId, SubjectRequest request);
    void deleteSubject(Long subjectId);
}
