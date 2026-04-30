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
import com.example.project.entity.User;
import com.example.project.dto.TeamDTO;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamDTO teamDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
         if (user == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(teamService.createTeam(teamDTO.getName(), user));
    }
}
