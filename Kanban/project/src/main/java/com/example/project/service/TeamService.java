package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Team;
import com.example.project.entity.User;
import com.example.project.repository.TeamRepository;
import com.example.project.repository.UserRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    public String createTeam(String name, Long userId) {

        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        // Fetch fresh user from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (teamRepository.existsByName(name)) {
            return "Team name already exists";
        }

        Team team = new Team();
        team.setName(name);
        team.setCreatedAt(java.time.LocalDateTime.now());
        team.setCreatedBy(user); // managed entity

        teamRepository.save(team);

        return "Team created successfully";
    }

    public Object getAllTeams() {
        return teamRepository.findAll();
    }

    public Object getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public String deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            return "Team not found";
        }
        teamRepository.deleteById(id);
        return "Team deleted successfully";
    }
}