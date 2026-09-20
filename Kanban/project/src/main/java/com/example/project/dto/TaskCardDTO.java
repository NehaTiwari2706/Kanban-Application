package com.example.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskCardDTO {
    private Long id;
    private String title;
    private String priority;
    private String status;
    private UserSummaryDTO assignee;
    private ProjectSummaryDTO project;
    private LocalDateTime createdAt;
    private LocalDate dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserSummaryDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(UserSummaryDTO assignee) {
        this.assignee = assignee;
    }

    public ProjectSummaryDTO getProject() {
        return project;
    }

    public void setProject(ProjectSummaryDTO project) {
        this.project = project;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
