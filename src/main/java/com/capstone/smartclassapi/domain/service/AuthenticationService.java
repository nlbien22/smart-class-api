package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.AuthenticationRequest;
import com.capstone.smartclassapi.api.dto.request.RegisterRequest;
import com.capstone.smartclassapi.api.dto.response.AuthenticationResponse;
import com.capstone.smartclassapi.core.security.JwtService;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.entity.enums.Provider;
import com.capstone.smartclassapi.domain.entity.UserEntity;
import com.capstone.smartclassapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new ResourceConflictException(String.format("Email %s already exists", request.getEmail()));
                });

        UserEntity user = UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.EMAIL)
                .build();
        userRepository.save(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(userRepository.findByUserId(3L).get().getEmail());
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email or password is incorrect"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .token(jwtService.generateToken(user))
                .build();
    }
}
