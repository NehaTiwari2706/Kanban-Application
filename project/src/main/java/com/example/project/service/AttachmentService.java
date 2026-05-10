package com.example.project.service;

import com.example.project.entity.Attachment;
import com.example.project.entity.Defect;
import com.example.project.entity.Task;
import com.example.project.entity.User;
import com.example.project.entity.UserStory;
import com.example.project.repository.AttachmentRepository;
import com.example.project.repository.DefectRepository;
import com.example.project.repository.TaskRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserStoryRepository;
import com.example.project.util.FileUploadUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/* AttachmentService

Handles:

validation
entity checks
DB save
uploadedBy
userStory/task/defect conditions

*/
@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefectRepository defectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private FileUploadUtility fileUploadUtility;

    // =========================================
    // Upload Attachment for Defect
    // =========================================
    public String uploadDefectAttachment(
            Long defectId,
            MultipartFile file,
            Long userId) {

        Defect defect = defectRepository.findById(defectId)
                .orElseThrow(() ->
                        new RuntimeException("Defect not found"));

        User uploadedBy = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean uploaded = fileUploadUtility.uploadFile(file);

        if (!uploaded) {
            throw new RuntimeException("Failed to upload file");
        }

        Attachment attachment = new Attachment();

        attachment.setFileUrl(file.getOriginalFilename());

        attachment.setFileType(file.getContentType());

        attachment.setUploadedBy(uploadedBy);

        attachment.setDefect(defect);

        attachmentRepository.save(attachment);

        return "Defect attachment uploaded successfully";
    }

    // =========================================
    // Upload Attachment for Task
    // =========================================
    public String uploadTaskAttachment(
            Long taskId,
            MultipartFile file,
            Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        User uploadedBy = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean uploaded = fileUploadUtility.uploadFile(file);

        if (!uploaded) {
            throw new RuntimeException("Failed to upload file");
        }

        Attachment attachment = new Attachment();

        attachment.setFileUrl(file.getOriginalFilename());

        attachment.setFileType(file.getContentType());

        attachment.setUploadedBy(uploadedBy);

        attachment.setTask(task);

        attachmentRepository.save(attachment);

        return "Task attachment uploaded successfully";
    }

    // =========================================
    // Upload Attachment for User Story
    // =========================================
    public String uploadUserStoryAttachment(
            Long userStoryId,
            MultipartFile file,
            Long userId) {

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() ->
                        new RuntimeException("User story not found"));

        User uploadedBy = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean uploaded = fileUploadUtility.uploadFile(file);

        if (!uploaded) {
            throw new RuntimeException("Failed to upload file");
        }

        Attachment attachment = new Attachment();

        attachment.setFileUrl(file.getOriginalFilename());

        attachment.setFileType(file.getContentType());

        attachment.setUploadedBy(uploadedBy);

        attachment.setUserStory(userStory);

        attachmentRepository.save(attachment);

        return "User story attachment uploaded successfully";
    }
}