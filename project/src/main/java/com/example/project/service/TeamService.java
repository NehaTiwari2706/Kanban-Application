package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Team;
import com.example.project.entity.User;
import com.example.project.repository.TeamRepository;
import com.example.project.dto.TeamDTO;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public String createTeam(String name, User user) {

        // Check if team name already exists
        if (teamRepository.existsByName(name)) {
            return "Team name already exists";
        }

        // Create and save the new team
        Team team = new Team();
        team.setName(name);
        team.setCreatedAt(java.time.LocalDateTime.now());
        team.setCreatedBy(user);

        teamRepository.save(team);

        return "Team created successfully";
    }
}