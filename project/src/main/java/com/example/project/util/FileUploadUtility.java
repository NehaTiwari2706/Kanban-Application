package com.example.project.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtility{

    static final String UPLOAD_DIR= "D:\\WEB DEV PROJECTS\\project\\src\\main\\resources\\static\\uploads";

    public boolean uploadfile(MultipartFile file){

        boolean f = false;

        try{

        } catch(Exception e){
            e.printStackTrace();
        }

        return f;
    }
}