package com.example.project.repository;

import com.example.project.entity.Iteration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IterationRepository extends JpaRepository<Iteration, Long> {

    @Query("SELECT COALESCE(MAX(i.iterationNumber), 0) FROM Iteration i WHERE i.team.id = :teamId")
    int findMaxIterationNumberByTeamId(Long teamId);

    List<Iteration> findByTeamId(Long teamId);

    @Query("""
           SELECT i
           FROM Iteration i
           WHERE i.startDate <= :today
             AND i.endDate >= :today
           ORDER BY i.startDate ASC
           """)
    List<Iteration> findCurrentIteration(LocalDate today);
}