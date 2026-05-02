package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project.entity.TeamMembers;
import java.util.List;


@Repository
public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long> {

    boolean existsByTeamIdAndUserId(Long teamId, Long userId);

    long deleteByTeamIdAndUserId(Long teamId, Long userId);

    List<TeamMembers> findByTeamId(Long teamId);
}
