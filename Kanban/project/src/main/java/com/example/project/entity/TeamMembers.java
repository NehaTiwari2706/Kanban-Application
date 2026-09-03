package com.example.project.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "team_members")
public class TeamMembers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "joined_at")
    private LocalDateTime joined_at;

    // Constructors
    public TeamMembers() {}

    public TeamMembers(Team team, User user, LocalDateTime joined_at) {
        this.team = team;
        this.user = user;
        this.joined_at = joined_at;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getJoined_at() {
        return joined_at;
    }
    public void setJoined_at(LocalDateTime joined_at) {
        this.joined_at = joined_at;
    }
}
