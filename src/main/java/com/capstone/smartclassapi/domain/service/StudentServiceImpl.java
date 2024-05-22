package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.StudentRequest;
import com.capstone.smartclassapi.api.dto.response.AllStudentResponse;
import com.capstone.smartclassapi.api.dto.response.StudentResponse;
import com.capstone.smartclassapi.domain.entity.ClassEntity;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.entity.enums.Provider;
import com.capstone.smartclassapi.domain.entity.enums.Role;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.ClassRepository;
import com.capstone.smartclassapi.domain.repository.StudentRepository;
import com.capstone.smartclassapi.domain.service.interfaces.ClassService;
import com.capstone.smartclassapi.domain.service.interfaces.StudentService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.capstone.smartclassapi.domain.constants.DefaultValue.DEFAULT_PASSWORD;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final ClassService classService;
    private final ClassRepository classRepository;

    @Override
    public StudentResponse getStudent(Long classId, Long studentId) {
        UserEntity studentEntity = studentRepository.findStudentById(classId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return StudentResponse.builder()
                .studentId(studentEntity.getUserId())
                .studentName(studentEntity.getFullName())
                .studentEmail(studentEntity.getEmail())
                .studentPhone(studentEntity.getPhoneNumber())
                .userCode(studentEntity.getUserCode())
                .imageKey(studentEntity.getImageKey())
                .build();
    }

    @Override
    public AllStudentResponse getAllStudents(Long classId, int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<UserEntity> listStudents = studentRepository.findAllStudents(
                        classId,
                        CommonValidation.escapeSpecialCharacters(keyword.trim()),
                        pageable);

        return AllStudentResponse.builder()
                .listStudents(listStudents.stream().map(StudentResponse::fromEntity).toList())
                .totalStudents(studentRepository.countAllStudents(
                        classId,
                        CommonValidation.escapeSpecialCharacters(keyword.trim())))
                .build();
    }

    @Override
    public void createStudent(Long classId, StudentRequest student) {
        ClassEntity classEntity = classService.findClassById(classId);
        boolean isExistEmail = studentRepository.existsByEmail(student.getStudentEmail()),
                isExistUserCode = studentRepository.existsByUserCodeAndUserCodeIsNotNull(student.getUserCode());

        UserEntity studentEntity;
        if (!isExistEmail && !isExistUserCode) {
            studentEntity = UserEntity.builder()
                    .fullName(student.getStudentName())
                    .email(student.getStudentEmail())
                    .phoneNumber(student.getStudentPhone())
                    .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .provider(Provider.EMAIL)
                    .role(Role.STUDENT)
                    .classes(List.of(classEntity))
                    .build();
            if (student.getUserCode() != null) {
                studentEntity.setUserCode(student.getUserCode());
            }
            if (student.getImageKey() != null) {
                studentEntity.setImageKey(student.getImageKey());
            }
            studentRepository.save(studentEntity);
            classEntity.getUsers().add(studentEntity);
        } else if (isExistEmail) {
            studentEntity = studentRepository.findByEmail(student.getStudentEmail());
            if (classEntity.getUsers().stream().map(UserEntity::getUserId).noneMatch(id -> id.equals(studentEntity.getUserId()))) {
                classEntity.getUsers().add(studentEntity);
            }
        } else {
            studentEntity = studentRepository.findByUserCodeAndUserCodeIsNotNull(student.getUserCode());
            if (classEntity.getUsers().stream().map(UserEntity::getUserId).noneMatch(id -> id.equals(studentEntity.getUserId()))) {
                classEntity.getUsers().add(studentEntity);
            }
        }

        classRepository.save(classEntity);
    }

    @Override
    public void createManyStudents(Long classId, List<StudentRequest> students) {
        ClassEntity classEntity = classService.findClassById(classId);
        for (StudentRequest student : students) {
            boolean isExistEmail = studentRepository.existsByEmail(student.getStudentEmail()),
                    isExistUserCode = studentRepository.existsByUserCodeAndUserCodeIsNotNull(student.getUserCode());
            UserEntity studentEntity;
            if (!isExistEmail && !isExistUserCode) {
                studentEntity = UserEntity.builder()
                        .fullName(student.getStudentName())
                        .email(student.getStudentEmail())
                        .phoneNumber(student.getStudentPhone())
                        .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                        .provider(Provider.EMAIL)
                        .role(Role.STUDENT)
                        .classes(List.of(classEntity))
                        .build();
                if (student.getUserCode() != null) {
                    studentEntity.setUserCode(student.getUserCode());
                }
                if (student.getImageKey() != null) {
                    studentEntity.setImageKey(student.getImageKey());
                }
                studentRepository.save(studentEntity);
                classEntity.getUsers().add(studentEntity);
            } else if (isExistEmail) {
                studentEntity = studentRepository.findByEmail(student.getStudentEmail());
                if (classEntity.getUsers().stream().map(UserEntity::getUserId).noneMatch(id -> id.equals(studentEntity.getUserId()))) {
                    classEntity.getUsers().add(studentEntity);
                }
            } else {
                studentEntity = studentRepository.findByUserCodeAndUserCodeIsNotNull(student.getUserCode());
                if (classEntity.getUsers().stream().map(UserEntity::getUserId).noneMatch(id -> id.equals(studentEntity.getUserId()))) {
                    classEntity.getUsers().add(studentEntity);
                }
            }
        }
        classRepository.save(classEntity);
    }

    @Override
    public void updateStudent(Long classId, Long studentId, StudentRequest student) {
        UserEntity studentEntity = studentRepository.findStudentById(classId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentEntity.setFullName(student.getStudentName());
        studentEntity.setEmail(student.getStudentEmail());
        studentEntity.setPhoneNumber(student.getStudentPhone());
        studentEntity.setUserCode(student.getUserCode());
        studentEntity.setImageKey(student.getImageKey());
        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudent(Long classId, Long studentId) {
        studentRepository.deleteByStudentId(classId, studentId);
    }

    @Override
    public void deleteAllStudents(Long classId) {
        studentRepository.deleteAllByClassId(classId);
    }


}
