package com.example.project.dto;

public class UsDTO {
    
    private String title;
    private String description;
    private String acceptanceCriteria;
    private String status;
    private String priority;
    private Long createdBy;
    private Long assignedTo;
    private Long iterationId;
    private Long teamId;
    private int estimatedTime;
    private int actualTime;

    //constructors
    public UsDTO() {}

    public UsDTO( String title, String description, String acceptanceCriteria,
                 String status, String priority, Long createdBy, Long assignedTo,
                 Long iterationId, Long teamId, int estimatedTime, int actualTime) {
        this.title = title;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
        this.status = status;
        this.priority = priority;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.iterationId = iterationId;
        this.teamId = teamId;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
    }

    // Getters and setters

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAcceptanceCriteria() { return acceptanceCriteria; }
    public void setAcceptanceCriteria(String acceptanceCriteria) { this.acceptanceCriteria = acceptanceCriteria; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public Long getIterationId() { return iterationId; }
    public void setIterationId(Long iterationId) { this.iterationId = iterationId; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }

    public int getActualTime() { return actualTime; }
    public void setActualTime(int actualTime) { this.actualTime = actualTime; }
}
