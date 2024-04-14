package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.core.ImageProperty;
import com.capstone.smartclassapi.domain.service.interfaces.ImageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageProperty properties;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase-private-key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .build();

            if (FirebaseApp.getApps().isEmpty())
                FirebaseApp.initializeApp(options);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getFacesImageUrl(String name) {
        return String.format(properties.getFacesImageUrl(), name);
    }

    @Override
    public String getQuestionImageUrl(String name, String path) {
        return String.format(properties.getQuestionImageUrl(), path, name);
    }

    @Override
    public String getGradedImageUrl(String name) {
        return String.format(properties.getGradedImageUrl(), name);
    }

    @Override
    public String save(MultipartFile file, String path) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(file.getOriginalFilename());

        bucket.create(path + name, file.getBytes(), file.getContentType());

        return name;
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName, String path) throws IOException {

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name, String path) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (ObjectUtils.isEmpty(name)) {
            throw new IOException("Invalid file name");
        }

        Blob blob = bucket.get(path + name);

        if (blob == null) {
            throw new IOException("File not found");
        }

        blob.delete();
    }
}