package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.repository.UserRepository;
import com.example.project.service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserRepository userRepository;

    // ==============================
    // Upload Attachment for Defect
    // ==============================
    @PostMapping("/defects/{id}")
    public ResponseEntity<String> uploadDefectAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String email) {

        try {

            // JWT provides the authenticated email; resolve it to the database user ID required by the service.
            Long userId = findUserId(email);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
            }

            validateFile(file);

            return ResponseEntity.ok(
                    attachmentService.uploadDefectAttachment(
                            id,
                            file,
                            userId
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ==============================
    // Upload Attachment for Task
    // ==============================
    @PostMapping("/tasks/{id}")
    public ResponseEntity<String> uploadTaskAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String email) {

        try {

            Long userId = findUserId(email);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
            }

            validateFile(file);

            return ResponseEntity.ok(
                    attachmentService.uploadTaskAttachment(
                            id,
                            file,
                            userId
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ====================================
    // Upload Attachment for User Story
    // ====================================
    @PostMapping("/user-stories/{id}")
    public ResponseEntity<String> uploadUserStoryAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String email) {

        try {

            Long userId = findUserId(email);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
            }

            validateFile(file);

            return ResponseEntity.ok(
                    attachmentService.uploadUserStoryAttachment(
                            id,
                            file,
                            userId
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id) {

        return attachmentService.downloadFile(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttachment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attachmentService.deleteAttachment(id));
    }

    // ==============================
    // Common File Validation
    // ==============================
    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Request must contain file");
        }

        // File size validation
        long MAX_FILE_SIZE = 10 * 1024 * 1024;

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds 10MB");
        }

        // File type validation
        String contentType = file.getContentType();

        if (
                !contentType.equals("image/png") &&
                !contentType.equals("image/jpeg") &&
                !contentType.equals("application/pdf") &&
                !contentType.equals(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        ) {

            throw new RuntimeException("Only PNG, JPG, JPEG, PDF, DOCX allowed");
        }

    }

    // Resolves the JWT principal to the user ID expected by AttachmentService.
    private Long findUserId(String email) {
        if (email == null) {
            return null;
        }
        return userRepository.findByEmail(email)
                .map(user -> user.getId())
                .orElse(null);
    }
}