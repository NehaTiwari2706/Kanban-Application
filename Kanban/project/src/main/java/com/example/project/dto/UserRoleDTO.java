package com.example.project.dto;


public class UserRoleDTO {
    
    private long id;
    private long user;
    private long role;

    public UserRoleDTO() {}

    public UserRoleDTO(long id, long user, long role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUser() { return user; }
    public void setUser(long user) { this.user = user; }

    public long getRole() { return role; }
    public void setRole(long role) { this.role = role; }

}
