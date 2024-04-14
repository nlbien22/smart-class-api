package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.domain.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    UserEntity findByEmail(String name);
    UserEntity findById(Long id);
    UserEntity getCurrentUser();
    Long getCurrentUserId();
}
