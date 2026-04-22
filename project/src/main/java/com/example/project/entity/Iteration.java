package com.example.project.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "iteration")
public class Iteration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iteration_number", nullable = false)
    private int iterationNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable= false)
    private String startDate;

    @Column(name = "end_date", nullable= false)
    private String endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Iteration() {}

    public Iteration(int iterationNumber, String name, String startDate, String endDate, String status, Team team, LocalDateTime createdAt) {
        this.iterationNumber = iterationNumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.team = team;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }
    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}