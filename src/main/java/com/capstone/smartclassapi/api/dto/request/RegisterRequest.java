package com.capstone.smartclassapi.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(description = "User full name", example = "John Doe")
    private String fullName;
    @Schema(description = "User email", example = "user1@gmail.com")
    private String email;
    @Schema(description = "User password", example = "123456")
    private String password;
}
