package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.KeyRequest;
import com.capstone.smartclassapi.api.dto.response.KeyResponse;
import com.capstone.smartclassapi.api.openapi.controller.KeyControllerOpenApi;
import com.capstone.smartclassapi.domain.constants.DefaultListParams;
import com.capstone.smartclassapi.domain.entity.KeyEntity;
import com.capstone.smartclassapi.domain.service.interfaces.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam/{examId}/key")
@RequiredArgsConstructor
public class KeyController implements KeyControllerOpenApi {
    private final KeyService keyService;
    private static final String DEFAULT_SORT_VALUE = "create_at";

    @Override
    public ResponseEntity<?> getKey(@PathVariable Long examId,@PathVariable Long keyId) {
        KeyResponse keyRes = KeyResponse.fromEntity(keyService.getKey(examId, keyId));
        return ResponseEntity.ok(keyRes);
    }

    @Override
    public ResponseEntity<?> getAllKeys(
        @PathVariable Long examId,
        @RequestParam(defaultValue = DefaultListParams.PAGE) int page,
        @RequestParam(defaultValue = DefaultListParams.SIZE) int size,
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "") String sortType,
        @RequestParam(defaultValue = DEFAULT_SORT_VALUE) String sortValue)
    {
        return ResponseEntity.ok(keyService.getAllKeys(examId, page, size, keyword, sortType, sortValue));
    }

    @Override
    public ResponseEntity<?> createKey(@PathVariable Long examId, @RequestBody KeyRequest key) {
        keyService.createKey(examId, key);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateKey(@PathVariable Long examId, @PathVariable Long keyId, @RequestBody KeyRequest key) {
        keyService.updateKey(examId, keyId, key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteKey(@PathVariable Long examId, @PathVariable Long keyId) {
        keyService.deleteKey(examId, keyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
