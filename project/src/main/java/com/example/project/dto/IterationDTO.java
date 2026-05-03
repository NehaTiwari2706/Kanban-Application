package com.example.project.dto;

import java.sql.Time;
import java.time.LocalDate;

public class IterationDTO {
    
    private Long id;
    private String iterationnumber;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long teamId;
    private Time created_at;

    public IterationDTO() {}

    public IterationDTO(Long id, String iterationnumber, String name, LocalDate startDate, LocalDate endDate, String status, Long teamId, Time created_at) {
        this.id = id;
        this.iterationnumber = iterationnumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.teamId = teamId;
        this.created_at = created_at;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIterationNumber() { return iterationnumber; }
    public void setIterationNumber(String iterationnumber) { this.iterationnumber = iterationnumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public Time getCreated_at() { return created_at; }
    public void setCreated_at(Time created_at) { this.created_at = created_at; }
}
