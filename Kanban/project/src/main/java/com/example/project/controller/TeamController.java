package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.TeamDTO;
import com.example.project.repository.UserRepository;
import com.example.project.service.TeamService;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTeam(
            @RequestBody TeamDTO teamDTO,
            @AuthenticationPrincipal String email) {
        // JWT authentication stores the email in Spring Security's authenticated principal.
        Long userId = userRepository.findByEmail(email)
                .map(user -> user.getId())
                .orElse(null);

        if (userId == null) {
            return ResponseEntity.status(401).body("Authenticated user not found");
        }

        // Previously, userId came from session.getAttribute("userId").
        return ResponseEntity.ok(teamService.createTeam(teamDTO.getName(), userId));
    }

    @GetMapping
    public ResponseEntity<?> getallTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.deleteTeam(id));
    }
}
