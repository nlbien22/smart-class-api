package com.capstone.smartclassapi.classes;

import com.capstone.smartclassapi.exception.ResourceConflictException;
import com.capstone.smartclassapi.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.exception.ResponseMessage;
import com.capstone.smartclassapi.user.UserService;
import com.capstone.smartclassapi.users_classes.UsersClassesService;
import com.capstone.smartclassapi.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;
    private final UserService userService;
    private final UsersClassesService usersClassesService;

    public ResponseMessage getClass(Long classId) {
        Class classModel = classRepository.findByClassId(userService.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        return ResponseMessage.builder()
                .status(200)
                .data(classModel)
                .build();
    }

    public Sort getSorting(String sortType, String sortValue) {
        if (sortType == null || sortType.equals("")) {
            sortValue = "created_at";
            sortType = "desc";
        }
        Sort sorting = Sort.by(sortValue);

        if (sortType != null) {
            sorting = sortType.equalsIgnoreCase("desc") ? sorting.descending() : sorting.ascending();
        }

        return sorting;
    }

    public ResponseMessage getAllClasses(int page, int size, String keyword, String sortType, String sortValue)
    {
        CommonValidation.validatePageAndSize(page, size);

        Sort sorting = getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);
        List<Class> listClasses = classRepository.findAllClassOfUserByName(
                userService.getCurrentUserId(),
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );

        return ResponseMessage.builder()
                .status(200)
                .data(listClasses)
                .additional(Map.of(
                        "total", classRepository.countByClassName(userService.getCurrentUserId(), keyword)
                        )
                )
                .build();
    }

    public ResponseMessage createClass(Class model) {
        classRepository.findByClassName(userService.getCurrentUserId(), model.getClassName())
                .ifPresent(classModel -> {
                    throw new ResourceConflictException("Class name already exists");
                });

        Class classModel = Class.builder()
                .className(model.getClassName())
                .build();
        classRepository.save(classModel);

        usersClassesService.createUsersClasses(
                userService.getCurrentUser().getId(),
                classModel.getClassId()
        );

        return ResponseMessage.builder()
                .status(201)
                .build();
    }

    public ResponseMessage updateClass(Long classId, Class model) {
        Class classModel = classRepository.findByClassId(userService.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        Optional<Class> classDb = classRepository
                .findByClassName(userService.getCurrentUserId(), model.getClassName());
        if (classDb.isPresent() && !classDb.get().getClassId().equals(classId)) {
            throw new ResourceConflictException("Class name already exists");
        }

        classModel.setClassName(model.getClassName());
        classRepository.save(classModel);

        return ResponseMessage.builder()
                .status(200)
                .build();
    }

    public ResponseMessage deleteClass(Long classId) {
        Class classModel = classRepository.findByClassId(userService.getCurrentUserId(), classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        classRepository.delete(classModel);

        return ResponseMessage.builder()
                .status(200)
                .build();
    }
}
