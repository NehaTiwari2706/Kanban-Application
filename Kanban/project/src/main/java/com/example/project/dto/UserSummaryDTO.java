package com.example.project.dto;

public class UserSummaryDTO {
    private Long id;
    private String name;
    private String initials;
    private String email;

    public UserSummaryDTO() {
    }

    public UserSummaryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.initials = initialsFrom(name);
    }

    public UserSummaryDTO(Long id, String name, String initials) {
        this.id = id;
        this.name = name;
        this.initials = initials;
    }

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

    public String getInitials() {
        return initials != null ? initials : initialsFrom(name);
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String initialsFrom(String value) {
        if (value == null || value.isBlank()) {
            return "U";
        }
        String[] parts = value.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }
}
