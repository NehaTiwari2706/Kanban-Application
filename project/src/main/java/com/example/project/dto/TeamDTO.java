package com.example.project.dto;

public class TeamDTO {
    
    private Long id;
    private String name;
    private Long createdById;
    private String createdAt;

    // Constructors
    public TeamDTO() {}

    public TeamDTO(Long id, String name, Long createdById, String createdAt) {
        this.id = id;
        this.name = name;
        this.createdById = createdById;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedById() {
        return createdById;
    }
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
