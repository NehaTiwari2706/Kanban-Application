package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.IterationDTO;
import com.example.project.entity.Iteration;
import com.example.project.entity.Team;
import com.example.project.repository.IterationRepository;
import com.example.project.repository.TeamRepository;
import com.example.project.entity.Iteration.Status;

@Service
public class IterationService {

    @Autowired
    private IterationRepository iterationRepository;

    @Autowired
    private TeamRepository teamRepository;

    public String createIteration(IterationDTO dto) {

        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            throw new RuntimeException("Start date and End date are required");
        }

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        // Fetch team
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // Auto-generate iteration number per team
        int maxIteration = iterationRepository.findMaxIterationNumberByTeamId(dto.getTeamId());
        int nextIterationNumber = maxIteration + 1;

        //  Convert DTO → Entity
        Iteration iteration = new Iteration();
        iteration.setIterationNumber(nextIterationNumber);
        iteration.setName(dto.getName());
        iteration.setStartDate(dto.getStartDate());
        iteration.setEndDate(dto.getEndDate());

        //  Convert string → enum safely
        try {
            iteration.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        } catch (Exception e) {
            throw new RuntimeException("Invalid status. Use PLANNED, ACTIVE, COMPLETED");
        }

        iteration.setTeam(team);

        iterationRepository.save(iteration);

        return "Iteration created successfully";
    }
}