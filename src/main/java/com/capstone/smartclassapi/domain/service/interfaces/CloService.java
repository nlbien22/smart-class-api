package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.CloRequest;
import com.capstone.smartclassapi.api.dto.response.AllCloResponse;
import com.capstone.smartclassapi.domain.entity.CloEntity;

public interface CloService {
    CloEntity getClo(Long subjectId, Long cloId);
    CloEntity getCloById(Long cloId);
    AllCloResponse getAllClos(Long subjectId, int page, int size, String keyword, String sortType, String sortValue);
    void createClo(Long subjectId, CloRequest request);
    void updateClo(Long subjectId, Long cloId, CloRequest request);
    void deleteClo(Long subjectId, Long cloId);
}
