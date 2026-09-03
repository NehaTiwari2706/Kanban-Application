package com.example.project.dto;

public class TeamMembersDTO {
    
    private Long id;
    private Long teamId;
    private Long userId;

    // Constructors
    public TeamMembersDTO() {}

    public TeamMembersDTO(Long id, Long teamId, Long userId) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
