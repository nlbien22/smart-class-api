package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.repository.UserRepository;
import com.capstone.smartclassapi.domain.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity findByEmail(String name) {
        return userRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getName());
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}