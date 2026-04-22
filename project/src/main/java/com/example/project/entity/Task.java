package com.example.project.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_number", nullable = false)
    private int taskNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "priority", nullable = false)
    private String priority;

    @ManyToOne
    @JoinColumn(name = "user_story_id", referencedColumnName = "id")
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Task() {}

    public Task(String title, String description, String status, UserStory userStory, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.userStory = userStory;
        this.createdAt = createdAt;
    }

    // Getters and setters
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public UserStory getUserStory() {
        return userStory;
    }
    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}   