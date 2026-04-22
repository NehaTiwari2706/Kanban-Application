package com.example.project.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(
    name = "user_story",
    uniqueConstraints = @UniqueConstraint(columnNames = {"iteration_id", "user_story_number"}),
    indexes = {
        @Index(name = "idx_user_story_iteration", columnList = "iteration_id"),
        @Index(name = "idx_user_story_assigned_to", columnList = "assigned_to")
    }
)
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_story_number", nullable = false)
    private int userStoryNumber;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "acceptance_criteria", nullable = false, length = 500)
    private String acceptanceCriteria;

    public enum Status { TODO, IN_PROGRESS, DONE }
    public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.TODO; // default

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority = Priority.MEDIUM; // default

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "iteration_id", nullable = false)
    private Iteration iteration;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "estimated_time", nullable = false)
    private int estimatedTime = 0;

    @Column(name = "actual_time", nullable = false)
    private int actualTime = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public UserStory() {}

    public UserStory(int userStoryNumber, String title, String description, String acceptanceCriteria,
                     Status status, Priority priority, User createdBy, User assignedTo,
                     Iteration iteration, Team team, int estimatedTime, int actualTime,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userStoryNumber = userStoryNumber;
        this.title = title;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
        this.status = status;
        this.priority = priority;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.iteration = iteration;
        this.team = team;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getUserStoryNumber() { return userStoryNumber; }
    public void setUserStoryNumber(int userStoryNumber) { this.userStoryNumber = userStoryNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAcceptanceCriteria() { return acceptanceCriteria; }
    public void setAcceptanceCriteria(String acceptanceCriteria) { this.acceptanceCriteria = acceptanceCriteria; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

    public Iteration getIteration() { return iteration; }
    public void setIteration(Iteration iteration) { this.iteration = iteration; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }

    public int getActualTime() { return actualTime; }
    public void setActualTime(int actualTime) { this.actualTime = actualTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}