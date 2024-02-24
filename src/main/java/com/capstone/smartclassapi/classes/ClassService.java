package com.capstone.smartclassapi.classes;

import com.capstone.smartclassapi.exception.ResourceConflictException;
import com.capstone.smartclassapi.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.exception.ResponseMessage;
import com.capstone.smartclassapi.user.UserService;
import com.capstone.smartclassapi.users_classes.UsersClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseMessage getAllClasses() {
        List<Class> classes = classRepository.findAllClassOfUser(userService.getCurrentUserId());
        return ResponseMessage.builder()
                .status(200)
                .data(classes)
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
