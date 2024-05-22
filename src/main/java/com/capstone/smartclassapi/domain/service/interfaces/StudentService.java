package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.StudentRequest;
import com.capstone.smartclassapi.api.dto.response.AllStudentResponse;
import com.capstone.smartclassapi.api.dto.response.StudentResponse;
import com.capstone.smartclassapi.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    StudentResponse getStudent(Long classId, Long studentId);
    AllStudentResponse getAllStudents(Long classId, int page, int size, String keyword, String sortType, String sortValue);
    void createStudent(Long classId, StudentRequest student);
    void createManyStudents(Long classId, List<StudentRequest> students);
    void updateStudent(Long classId, Long studentId, StudentRequest student);
    void deleteStudent(Long classId, Long studentId);
    void deleteAllStudents(Long classId);
}
