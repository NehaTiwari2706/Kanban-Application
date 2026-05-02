package com.example.project.service;

import org.springframework.stereotype.Service;
import com.example.project.entity.Team;
import com.example.project.entity.User;
import com.example.project.entity.TeamMembers;
import com.example.project.repository.TeamMembersRepository;
import com.example.project.repository.TeamRepository;
import com.example.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import com.example.project.dto.UserDTO;

@Service
public class TeamMemberService {

    private final TeamMembersRepository teamMembersRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamMemberService(TeamMembersRepository teamMembersRepository,
                             TeamRepository teamRepository,
                             UserRepository userRepository) {
        this.teamMembersRepository = teamMembersRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

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

    @Transactional
    public void removeTeamMemberFromTeam(Long teamId, Long userId) {

        if (!teamMembersRepository.existsByTeamIdAndUserId(teamId, userId)) {
            throw new RuntimeException("User not in team");
        }

        long deleted = teamMembersRepository.deleteByTeamIdAndUserId(teamId, userId);

        if (deleted == 0) {
            throw new RuntimeException("Delete failed");
        }
    }

    public List<UserDTO> getTeamMembersofTeam(Long teamId) {

        List<TeamMembers> members = teamMembersRepository.findByTeamId(teamId);

        return members.stream()
                .map(member -> mapToUserDTO(member.getUser()))
                .toList();
    }

    // 🔥 MAPPER METHOD (important)
    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDomain()
        );
    }
}