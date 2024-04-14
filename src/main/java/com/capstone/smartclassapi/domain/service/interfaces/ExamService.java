package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.ExamRequest;
import com.capstone.smartclassapi.api.dto.response.AllExamsResponse;
import com.capstone.smartclassapi.api.dto.response.ExamResponse;

public interface ExamService {
    ExamResponse getExam(Long examId);
    ExamResponse getExamsOfClass(Long classId, Long examId);
    AllExamsResponse getAllExams(
            int page,
            int size,
            String keyword,
            String sortType,
            String sortValue
    );
    AllExamsResponse getAllExamsOfClass(
            Long classId,
            int page,
            int size,
            String keyword,
            String sortType,
            String sortValue
    );
    void createExam(ExamRequest examRequest);
    void createExamOfClass(Long classId, ExamRequest examRequest);
    void updateExam(Long examId, ExamRequest examRequest);
    void updateExamOfClass(Long classId, Long examId, ExamRequest examRequest);
    void deleteExam(Long examId);
    void deleteExamOfClass(Long classId, Long examId);

}
