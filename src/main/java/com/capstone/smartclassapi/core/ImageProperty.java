package com.capstone.smartclassapi.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class ImageProperty {
    private String bucketName;
    private String facesImageUrl;
    private String gradedImageUrl;
    private String questionImageUrl;
}
