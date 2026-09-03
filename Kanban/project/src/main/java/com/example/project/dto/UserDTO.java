package com.example.project.dto;

import java.time.LocalDateTime;

public class UserDTO{
    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
    private String domain;

    public UserDTO() {}

    public UserDTO(Long id, String fullName, String email, LocalDateTime createdAt, String domain) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
        this.domain = domain;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }  
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
}