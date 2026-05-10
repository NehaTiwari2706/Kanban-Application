package com.example.project.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/* FileUploadUtility

ONLY handles:
saving files
deleting files
generic file operations 

*/
@Component
public class FileUploadUtility {

    static final String UPLOAD_DIR =
            "D:\\WEB DEV PROJECTS\\project\\src\\main\\resources\\static\\uploads";

    public boolean uploadFile(MultipartFile file) {

        boolean f = false;

        try {

            Files.copy(
                    file.getInputStream(),
                    Paths.get(
                            UPLOAD_DIR
                            + File.separator
                            + file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING
            );

            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}