package com.example.project.dto;

public class UserRegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String domain;

    public UserRegisterRequest() {}

    public UserRegisterRequest(String fullName, String email, String password, String domain) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = password;
        this.domain = domain;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
}
