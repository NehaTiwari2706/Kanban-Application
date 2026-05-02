package com.example.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.project.service.TeamMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.project.dto.UserDTO;
import java.util.List;

@RestController
@RequestMapping("/api/team-members")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamMembersController {

    private final TeamMemberService teamMemberService;

    public TeamMembersController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping("/team/{teamId}/user/{userId}")
    public ResponseEntity<String> addTeamMemberToTeam(
            @PathVariable Long teamId,
            @PathVariable Long userId) {

        String response = teamMemberService.addTeamMemberToTeam(teamId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/team/{teamId}/members")
    public ResponseEntity<List<UserDTO>> getTeamMembersofTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamMemberService.getTeamMembersofTeam(teamId));
    }

    @DeleteMapping("/team/{teamId}/user/{userId}")
    public ResponseEntity<Void> removeTeamMemberFromTeam(
            @PathVariable Long teamId,
            @PathVariable Long userId) {

        teamMemberService.removeTeamMemberFromTeam(teamId, userId);
        return ResponseEntity.noContent().build();
    }
}