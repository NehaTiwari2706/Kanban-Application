package com.example.project.dto;

import java.sql.Time;

public class IterationDTO {
    
    private Long id;
    private String iteration_number;
    private String name;
    private String start_date;
    private String end_date;
    private String status;
    private Long team_id;
    private Time created_at;

    public IterationDTO() {}

    public IterationDTO(Long id, String iteration_number, String name, String start_date, String end_date, String status, Long team_id, Time created_at) {
        this.id = id;
        this.iteration_number = iteration_number;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.team_id = team_id;
        this.created_at = created_at;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIteration_number() { return iteration_number; }
    public void setIteration_number(String iteration_number) { this.iteration_number = iteration_number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStart_date() { return start_date; }
    public void setStart_date(String start_date) { this.start_date = start_date; }

    public String getEnd_date() { return end_date; }
    public void setEnd_date(String end_date) { this.end_date = end_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getTeam_id() { return team_id; }
    public void setTeam_id(Long team_id) { this.team_id = team_id; }

    public Time getCreated_at() { return created_at; }
    public void setCreated_at(Time created_at) { this.created_at = created_at; }
}
