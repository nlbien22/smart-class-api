package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.KeyRequest;
import com.capstone.smartclassapi.api.dto.response.AllKeysResponse;
import com.capstone.smartclassapi.api.dto.response.KeyResponse;
import com.capstone.smartclassapi.domain.entity.KeyEntity;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.KeyRepository;
import com.capstone.smartclassapi.domain.service.interfaces.KeyService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeyServiceImp implements KeyService {
    private final KeyRepository keyRepository;

    @Override
    public KeyEntity getKey(Long examId, Long keyId) {
        return keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
    }

    @Override
    public AllKeysResponse getAllKeys(Long examId, int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<KeyEntity> listKeys = keyRepository.findAllKeysByExamId(
                examId,
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return AllKeysResponse.builder()
                .listKeys(listKeys.stream().map(KeyResponse::fromEntity).toList())
                .totalKeys(keyRepository.countKeyByExamId(examId, CommonValidation.escapeSpecialCharacters(keyword.trim())))
                .build();
    }

    @Override
    public void createKey(Long examId, KeyRequest request) {
        keyRepository.findByKeyCode(examId, request.getKeyCode())
                .ifPresent(keyEntity -> {
                    throw new ResourceNotFoundException("Key already exists");
                });
        KeyEntity keyEntity = KeyEntity.builder()
                .keyCode(request.getKeyCode())
                .typeKey(request.getTypeKey())
//                .autoQuestions(request.getAutoQuestions())
                .build();
        keyRepository.save(keyEntity);
    }

    @Override
    public void updateKey(Long examId, Long keyId, KeyRequest request) {
        KeyEntity keyEntity = keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        Optional<KeyEntity> keyEntityOptional = keyRepository
                .findByKeyCode(examId, request.getKeyCode());
        if (keyEntityOptional.isPresent() && !keyEntityOptional.get().getKeyCode().equals(request.getKeyCode())) {
            throw new ResourceConflictException("Class name already exists");
        }

        keyEntity.setKeyCode(request.getKeyCode());
        keyRepository.save(keyEntity);
    }

    @Override
    public void deleteKey(Long examId, Long keyId) {
        KeyEntity keyEntity = keyRepository.findKeyById(examId, keyId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        keyRepository.delete(keyEntity);
    }
}
