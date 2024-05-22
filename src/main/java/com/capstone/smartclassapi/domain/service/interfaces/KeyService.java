package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.KeyRequest;
import com.capstone.smartclassapi.api.dto.response.AllKeysResponse;
import com.capstone.smartclassapi.api.dto.response.KeyResponse;
import com.capstone.smartclassapi.domain.entity.KeyEntity;

public interface KeyService {
    KeyEntity getKey(Long examId, Long keyId);
    AllKeysResponse getAllKeys(Long examId, int page, int size, String keyword, String sortType, String sortValue);
    void createKey(Long examId, KeyRequest request);
    void updateKey(Long examId, Long keyId, KeyRequest request);
    void deleteKey(Long examId, Long keyId);

    KeyResponse getKeyResponse(Long examId, Long keyId);

    KeyEntity findByKeyCode(Long examId, String keyCode);
}
