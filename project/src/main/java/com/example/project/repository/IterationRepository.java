package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.project.entity.Iteration;

public interface IterationRepository extends JpaRepository<Iteration, Long> {

    @Query("SELECT COALESCE(MAX(i.iterationNumber), 0) FROM Iteration i WHERE i.team.id = :teamId")
    int findMaxIterationNumberByTeamId(Long teamId);
}