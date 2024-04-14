package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.dto.response.AllExamsResponse;
import com.capstone.smartclassapi.api.dto.response.ClassResponse;
import com.capstone.smartclassapi.api.dto.response.ExamResponse;
import com.capstone.smartclassapi.domain.entity.ExamEntity;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.ExamRepository;
import com.capstone.smartclassapi.domain.service.interfaces.ExamService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImp implements ExamService {
    private final ExamRepository examRepository;
    private final TypeExamServiceImp typeExamServiceImp;
    private final ClassServiceImp classServiceImp;
    private final UserServiceImp userServiceImp;

    @Override
    public ExamResponse getExam(Long examId) {
        return ExamResponse.fromEntity(examRepository.findExamByUserId(userServiceImp.getCurrentUser().getUserId(), examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found")));
    }

    @Override
    public ExamResponse getExamsOfClass(Long classId, Long examId) {
        return ExamResponse.fromEntity(examRepository.findExamByClassId(classId, examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found")));
    }

    @Override
    public AllExamsResponse getAllExams(int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<ExamEntity> exams = examRepository.findAllExamsByUserId(
                userServiceImp.getCurrentUserId(),
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return AllExamsResponse.builder()
                .exams(exams.stream().map(ExamResponse::fromEntity).toList())
                .totalExams(examRepository.countExamNameByUserId(
                        userServiceImp.getCurrentUserId(),
                        CommonValidation.escapeSpecialCharacters(keyword.trim()))
                )
                .build();
    }

    @Override
    public AllExamsResponse getAllExamsOfClass(Long classId, int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<ExamEntity> exams = examRepository.findAllExamsByClassId(
                classId,
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return AllExamsResponse.builder()
                .exams(exams.stream().map(ExamResponse::fromEntity).toList())
                .totalExams(examRepository.countExamNameByClassId(
                        classId,
                        CommonValidation.escapeSpecialCharacters(keyword.trim()))
                )
                .build();
    }

    @Override
    public void createExam(ExamRequest examRequest) {
        UserEntity userEntity = userServiceImp.getCurrentUser();
        ExamEntity examEntity = ExamEntity.builder()
                .examName(examRequest.getExamName())
                .examDate(examRequest.getExamDate())
                .numExam(examRequest.getNumExam())
                .pointExam(examRequest.getPointExam())
                .typeExam(typeExamServiceImp.getTypeExam(examRequest.getTypeExamId()))
                .users(List.of(userEntity))
                .build();
        examRepository.save(examEntity);
    }

    @Override
    public void createExamOfClass(Long classId, ExamRequest examRequest) {
        ClassResponse classResponse = classId != null ? classServiceImp.getClass(classId) : null;
        UserEntity userEntity = userServiceImp.getCurrentUser();
        ExamEntity examEntity = ExamEntity.builder()
                .examName(examRequest.getExamName())
                .examDate(examRequest.getExamDate())
                .numExam(examRequest.getNumExam())
                .pointExam(examRequest.getPointExam())
                .typeExam(typeExamServiceImp.getTypeExam(examRequest.getTypeExamId()))
                .users(List.of(userEntity))
                .classEntity(classResponse != null ? ClassResponse.toEntity(classResponse) : null)
                .build();

        examRepository.save(examEntity);
    }

    @Override
    public void updateExam(Long examId, ExamRequest examRequest) {
        UserEntity userEntity = userServiceImp.getCurrentUser();
        ExamEntity examEntity = examRepository.findExamByUserId(userEntity.getUserId(), examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        examRepository.save(update(examEntity, examRequest));
    }

    @Override
    public void updateExamOfClass(Long classId, Long examId, ExamRequest examRequest) {
        classServiceImp.getClass(classId);
        ExamEntity examEntity = examRepository.findExamByClassId(classId, examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        examRepository.save(update(examEntity, examRequest));
    }

    @Override
    public void deleteExam(Long examId) {
        UserEntity userEntity = userServiceImp.getCurrentUser();
        ExamEntity exam = examRepository.findExamByUserId(userEntity.getUserId(), examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        examRepository.delete(exam);

    }

    @Override
    public void deleteExamOfClass(Long classId, Long examId) {
        classServiceImp.getClass(classId);
        ExamEntity exam = examRepository.findExamByClassId(classId, examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        examRepository.delete(exam);
    }

    public ExamEntity update(ExamEntity examEntity, ExamRequest examRequest) {
        examEntity.setExamName(examRequest.getExamName() != null ? examRequest.getExamName() : examEntity.getExamName());
        examEntity.setExamDate(examRequest.getExamDate() != null ? examRequest.getExamDate() : examEntity.getExamDate());
        examEntity.setNumExam(examRequest.getNumExam() != null ? examRequest.getNumExam() : examEntity.getNumExam());
        examEntity.setPointExam(examRequest.getPointExam() != null ? examRequest.getPointExam() : examEntity.getPointExam());
        examEntity.setTypeExam(examRequest.getTypeExamId() != null ? typeExamServiceImp.getTypeExam(examRequest.getTypeExamId()) : examEntity.getTypeExam());
        return examEntity;

    }
}
