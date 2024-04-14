package com.capstone.smartclassapi.domain.service.interfaces;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public interface ImageService {

    String getFacesImageUrl(String name);

    String getQuestionImageUrl(String name, String path);

    String getGradedImageUrl(String name);

    String save(MultipartFile file, String path) throws IOException;

    String save(BufferedImage bufferedImage, String originalFileName, String path) throws IOException;

    void delete(String name, String path) throws IOException;

    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID() + getExtension(originalFileName);
    }

    default byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {
        try (
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, byteArray);
            byteArray.flush();

            return byteArray.toByteArray();

        } catch (IOException e) {
            throw e;
        }
    }

}
