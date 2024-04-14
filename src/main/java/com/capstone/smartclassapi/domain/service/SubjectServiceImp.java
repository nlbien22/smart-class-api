package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.SubjectRequest;
import com.capstone.smartclassapi.api.dto.response.AllSubjectsResponse;
import com.capstone.smartclassapi.api.dto.response.SubjectResponse;
import com.capstone.smartclassapi.domain.entity.SubjectEntity;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.SubjectRepository;
import com.capstone.smartclassapi.domain.service.interfaces.SubjectService;
import com.capstone.smartclassapi.domain.service.interfaces.UserService;
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
public class SubjectServiceImp implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    @Override
    public SubjectEntity getSubject(Long subjectId) {
        return subjectRepository.findSubjectById(userService.getCurrentUserId(), subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
    }

    @Override
    public AllSubjectsResponse getAllSubjects(int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<SubjectEntity> subjects = subjectRepository.findAllSubjectsOfUser(
                userService.getCurrentUserId(),
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return AllSubjectsResponse.builder()
                .subjects(subjects.stream().map(SubjectResponse::fromEntity).toList())
                .totalSubjects(subjectRepository.countByUserId(
                        userService.getCurrentUserId(),
                        CommonValidation.escapeSpecialCharacters(keyword.trim()))
                )
                .build();
    }

    @Override
    public void createSubject(SubjectRequest request) {
        subjectRepository.findBySubjectName(userService.getCurrentUserId(), request.getSubjectName())
                .ifPresent(classModel -> {
                    throw new ResourceConflictException(String.format("Subject %s already exists", request.getSubjectName()));
                });
        UserEntity user = userService.getCurrentUser();
        SubjectEntity subjectEntity = SubjectEntity.builder()
                .subjectName(request.getSubjectName())
                .users(List.of(user))
                .build();
        subjectRepository.save(subjectEntity);
    }

    @Override
    public void updateSubject(Long subjectId, SubjectRequest request) {
        SubjectEntity subjectEntity = subjectRepository.findSubjectById(userService.getCurrentUserId(), subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Optional<SubjectEntity> subjectOptional = subjectRepository
                .findBySubjectName(userService.getCurrentUserId(), request.getSubjectName());
        if (subjectOptional.isPresent() && !subjectOptional.get().getSubjectId().equals(subjectId)) {
            throw new ResourceConflictException(String.format("Subject %s already exists", request.getSubjectName()));
        }

        subjectEntity.setSubjectName(request.getSubjectName());
        subjectRepository.save(subjectEntity);
    }

    @Override
    public void deleteSubject(Long subjectId) {
        SubjectEntity subjectEntity = subjectRepository.findSubjectById(userService.getCurrentUserId(), subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        subjectRepository.delete(subjectEntity);
    }
}
