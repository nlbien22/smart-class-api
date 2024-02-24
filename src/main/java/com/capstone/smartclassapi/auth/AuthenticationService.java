package com.capstone.smartclassapi.auth;

import com.capstone.smartclassapi.config.JwtService;
import com.capstone.smartclassapi.exception.ResourceConflictException;
import com.capstone.smartclassapi.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.exception.ResponseMessage;
import com.capstone.smartclassapi.user.Provider;
import com.capstone.smartclassapi.user.User;
import com.capstone.smartclassapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseMessage register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new ResourceConflictException(String.format("Email %s already exists", request.getEmail()));
                });

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.EMAIL)
                .build();
        userRepository.save(user);

        return ResponseMessage.builder()
                .status(CREATED.value())
                .build();
    }

    public ResponseMessage authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email or password is incorrect"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AuthenticationResponse response = AuthenticationResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .token(jwtService.generateToken(user))
                .build();

        return ResponseMessage.builder()
                .status(OK.value())
                .data(response)
                .build();
    }
}
