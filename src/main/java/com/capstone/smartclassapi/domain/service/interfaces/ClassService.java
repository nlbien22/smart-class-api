package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.ClassRequest;
import com.capstone.smartclassapi.api.dto.response.AllClassResponse;
import com.capstone.smartclassapi.api.dto.response.ClassResponse;
import com.capstone.smartclassapi.domain.entity.ClassEntity;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    ClassResponse getClass(Long classId);
    AllClassResponse getAllClasses(int page, int size, String keyword, String sortType, String sortValue);
    void createClass(ClassRequest request);
    void updateClass(Long classId, ClassRequest request);
    void deleteClass(Long classId);
    boolean isTeacherOfClass(Long classId);
}
