package com.capstone.smartclassapi.auth;

import com.capstone.smartclassapi.config.JwtService;
import com.capstone.smartclassapi.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.exception.ResponseMessage;
import com.capstone.smartclassapi.user.Provider;
import com.capstone.smartclassapi.user.Role;
import com.capstone.smartclassapi.user.User;
import com.capstone.smartclassapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        userRepository.findByEmail(request.getEmail()).orElseThrow(
                    () -> new ResourceNotFoundException(String.format("Email %s already exists", request.getEmail())));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.EMAIL)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return ResponseMessage.builder()
                .status(CREATED.value())
                .data(jwtToken)
                .build();
    }

    public ResponseMessage authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Email %s not found", request.getEmail())));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(user);
        return ResponseMessage.builder()
                .status(OK.value())
                .data(jwtToken)
                .build();
    }
}
