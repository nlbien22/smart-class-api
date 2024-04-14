package com.capstone.smartclassapi.api.openapi.controller;

import com.capstone.smartclassapi.api.dto.request.AuthenticationRequest;
import com.capstone.smartclassapi.api.dto.request.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Authentication")
public interface AuthenticationControllerOpenApi {
    @Operation(summary = "Register a new user")
    ResponseEntity<?> register(RegisterRequest request);

    @Operation(summary = "Authenticate a user")
    ResponseEntity<?> authenticate(AuthenticationRequest request);
}
