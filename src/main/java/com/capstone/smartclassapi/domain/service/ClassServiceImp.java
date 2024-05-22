package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.ClassRequest;
import com.capstone.smartclassapi.api.dto.response.AllClassResponse;
import com.capstone.smartclassapi.api.dto.response.ClassResponse;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.entity.ClassEntity;
import com.capstone.smartclassapi.domain.repository.ClassRepository;
import com.capstone.smartclassapi.domain.service.interfaces.ClassService;
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
public class ClassServiceImp implements ClassService {
    private final ClassRepository classRepository;
    private final UserServiceImp userServiceImp;

    @Override
    public ClassResponse getClass(Long classId) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        return ClassResponse.builder()
                .classId(classEntity.getClassId())
                .className(classEntity.getClassName())
                .createdAt(classEntity.getCreatedAt())
                .build();
    }

    @Override
    public AllClassResponse getAllClasses(int page, int size, String keyword, String sortType, String sortValue)
    {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<ClassEntity> listClasses = classRepository.findAllClassOfUserByName(
                userServiceImp.getCurrentUserId(),
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return AllClassResponse.builder()
                .listClasses(listClasses.stream().map(ClassResponse::fromEntity).toList())
                .totalClasses(classRepository.countByClassName(
                        userServiceImp.getCurrentUserId(),
                        CommonValidation.escapeSpecialCharacters(keyword.trim()))
                )
                .build();
    }

    @Override
    public void createClass(ClassRequest request) {
        classRepository.findByClassName(userServiceImp.getCurrentUserId(), request.getClassName())
                .ifPresent(classModel -> {
                    throw new ResourceConflictException("Class name already exists");
                });
        UserEntity user = userServiceImp.getCurrentUser();
        ClassEntity classModel = ClassEntity.builder()
                .className(request.getClassName())
                .users(List.of(user))
                .build();
        classRepository.save(classModel);
    }

    @Override
    public void updateClass(Long classId, ClassRequest request) {
        ClassEntity classModel = classRepository.findByClassId(userServiceImp.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        Optional<ClassEntity> classDb = classRepository
                .findByClassName(userServiceImp.getCurrentUserId(), request.getClassName());
        if (classDb.isPresent() && !classDb.get().getClassId().equals(classId)) {
            throw new ResourceConflictException("Class name already exists");
        }

        classModel.setClassName(request.getClassName());
        classRepository.save(classModel);
    }

    @Override
    public void deleteClass(Long classId) {
        ClassEntity classModel = classRepository.findByClassId(userServiceImp.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        classRepository.delete(classModel);
    }

    @Override
    public boolean isTeacherOfClass(Long classId) {
        return classRepository.findByClassId(userServiceImp.getCurrentUserId(), classId).isPresent();
    }

    @Override
    public void deleteAllClasses() {
        if (!classRepository.findAllClassOfUserByName(userServiceImp.getCurrentUserId(), "", PageRequest.of(0, 1)).isEmpty())
            classRepository.deleteAllClassByUserId(userServiceImp.getCurrentUserId());
    }

    @Override
    public ClassEntity findClassById(Long classId) {
        return classRepository.findByClassId(userServiceImp.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
    }
}
