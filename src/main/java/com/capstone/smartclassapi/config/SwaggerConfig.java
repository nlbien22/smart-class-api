package com.capstone.smartclassapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "GradeXpert",
                        email = "nlbien.22@gmail.com",
                        url = "https://localhost:8003/"
                ),
                description = "That la vai ",
                title = "GradeXpert API",
                version = "1.0"
        ),
        servers = {@Server(
                description = "Local Test",
                url = "https://localhost:8003/api/v1"
        ), @Server(
                description = "Production Test",
                url = "https://https://smartclass.id.vn/:8003/api/v1"
        )},
        security = {@SecurityRequirement(
                name = "bearerAuth"
        )}
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class SwaggerConfig {
    public SwaggerConfig() {
    }
}
