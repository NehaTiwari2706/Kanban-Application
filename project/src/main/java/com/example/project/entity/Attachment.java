package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment", uniqueConstraints = @UniqueConstraint(columnNames = {"user_story_id", "task_id", "defect_id"}), indexes = {
    @Index(name = "idx_attachment_user_story", columnList = "user_story_id"),
    @Index(name = "idx_attachment_task", columnList = "task_id"),
    @Index(name = "idx_attachment_defect", columnList = "defect_id"),
    @Index(name = "idx_attachment_uploaded_by", columnList = "uploaded_by")
})
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    @Column(name = "file_type", length = 100)
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @ManyToOne
    @JoinColumn(name = "user_story_id")
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "defect_id")
    private Defect defect;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Attachment() {}

    public Attachment(String fileUrl, String fileType, User uploadedBy, UserStory userStory, Task task, Defect defect) {
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.uploadedBy = uploadedBy;
        this.userStory = userStory;
        this.task = task;
        this.defect = defect;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public UserStory getUserStory() {
        return userStory;
    }
    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    public Defect getDefect() {
        return defect;
    }
    public void setDefect(Defect defect) {
        this.defect = defect;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
