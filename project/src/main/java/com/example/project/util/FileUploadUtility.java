package com.example.project.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtility {


    private static final String UPLOAD_DIR = "D:/uploads";

    // =========================
    // Upload File
    // =========================
    public String uploadFile(MultipartFile file) {

        try {

            String fileName =
                    System.currentTimeMillis()
                    + "_"
                    + file.getOriginalFilename();

            String filePath =
                    UPLOAD_DIR
                    + File.separator
                    + fileName;

            Files.copy(
                    file.getInputStream(),
                    Paths.get(filePath),
                    StandardCopyOption.REPLACE_EXISTING
            );

            // STORE ONLY FILENAME IN DB
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file");
        }
    }

    // =========================
    // Download File
    // =========================
    public Resource downloadFile(String fileName) {

        try {

            Path path =
                    Paths.get(UPLOAD_DIR, fileName);

            Resource resource =
                    new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }

            return resource;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to download file");
        }
    }
}