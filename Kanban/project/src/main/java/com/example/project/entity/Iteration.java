package com.example.project.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDate  startDate;

    @Column(name = "end_date", nullable= false)
    private LocalDate  endDate;

    public enum Status {
        PLANNED,
        ACTIVE,
        COMPLETED
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PLANNED; // default

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "iteration")
    private List<UserStory> userStories;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructors
    public Iteration() {}

    public Iteration(int iterationNumber, String name, LocalDate startDate, LocalDate endDate, Status status, Team team) {
        this.iterationNumber = iterationNumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.team = team;
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

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
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

    public List<UserStory> getUserStories() {
        return userStories;
    }
    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

}