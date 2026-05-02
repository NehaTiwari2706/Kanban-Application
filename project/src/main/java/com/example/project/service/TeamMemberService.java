package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project.entity.Team;
import com.example.project.entity.User;
import com.example.project.entity.TeamMembers;
import com.example.project.repository.TeamMembersRepository;
import com.example.project.repository.TeamRepository;
import com.example.project.repository.UserRepository;

@Service
public class TeamMemberService {

    @Autowired
    TeamMembersRepository teamMembersRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;
    
    public String addTeamMemberToTeam(Long teamId, Long userId) {

        if (teamMembersRepository.existsByTeamIdAndUserId(teamId, userId)) {
            throw new RuntimeException("User already in team");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TeamMembers member = new TeamMembers();
        member.setTeam(team);
        member.setUser(user);
        member.setJoined_at(java.time.LocalDateTime.now());

        teamMembersRepository.save(member);

        return "Member added successfully";
    }
}
