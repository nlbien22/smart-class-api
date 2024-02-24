package com.capstone.smartclassapi.users_classes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersClassesService {
    private final UsersClassesRepository usersClassesRepository;

    public UsersClasses createUsersClasses(Long userId, Long classId) {
        UsersClasses usersClasses = UsersClasses.builder()
                .id(userId)
                .classId(classId)
                .build();
        return usersClassesRepository.save(usersClasses);
    }
}
