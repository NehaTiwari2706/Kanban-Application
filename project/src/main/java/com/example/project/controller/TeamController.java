package com.example.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.project.service.TeamService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.project.dto.TeamDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamDTO teamDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("Session ID: " + session.getId());
        System.out.println("User: " + session.getAttribute("user"));
         if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
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
