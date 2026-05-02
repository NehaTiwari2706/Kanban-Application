package com.example.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.service.TeamMemberService;


@RestController
@RequestMapping("/api/team-members")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamMembersController {
    
    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/team/{teamId}/user/{userId}")
    public ResponseEntity<?> addTeamMemberToTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        // Logic to add a user to a team
        teamMemberService.addTeamMemberToTeam(teamId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
