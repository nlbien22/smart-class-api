package com.capstone.smartclassapi.api.v1.controller;

import com.capstone.smartclassapi.api.dto.request.AuthenticationRequest;
import com.capstone.smartclassapi.api.dto.request.RegisterRequest;
import com.capstone.smartclassapi.api.openapi.controller.AuthenticationControllerOpenApi;
import com.capstone.smartclassapi.domain.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerOpenApi {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.authenticate(request));
    }
}
