package com.example.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.UsDTO;
import com.example.project.entity.Iteration;
import com.example.project.entity.Team;
import com.example.project.entity.User;
import com.example.project.entity.UserStory;
import com.example.project.entity.UserStory.Status;
import com.example.project.entity.UserStory.Priority;

import com.example.project.repository.IterationRepository;
import com.example.project.repository.TeamRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserStoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class UserStoryService {

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private IterationRepository iterationRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String createUserStory(UsDTO dto) {

        // Fetch required entities
        Iteration iteration = iterationRepository.findById(dto.getIterationId())
                .orElseThrow(() -> new RuntimeException("Iteration not found"));

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User createdBy = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User assignedTo = userRepository.findById(dto.getAssignedTo())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        // Convert enums safely
        Status status;
        try {
            status = Status.valueOf(dto.getStatus().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid status. Use TODO, IN_PROGRESS, DONE");
        }

        Priority priority;
        try {
            priority = Priority.valueOf(dto.getPriority().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid priority. Use LOW, MEDIUM, HIGH, CRITICAL");
        }

        // Retry mechanism to handle race condition
        int attempts = 0;
        int maxRetries = 3;

        while (attempts < maxRetries) {
            try {
                int maxNumber = userStoryRepository
                        .findMaxUserStoryNumberByIterationId(dto.getIterationId());

                int nextNumber = maxNumber + 1;

                UserStory userStory = new UserStory();
                userStory.setUserStoryNumber(nextNumber);
                userStory.setTitle(dto.getTitle());
                userStory.setDescription(dto.getDescription());
                userStory.setAcceptanceCriteria(dto.getAcceptanceCriteria());
                userStory.setStatus(status);
                userStory.setPriority(priority);
                userStory.setCreatedBy(createdBy);
                userStory.setAssignedTo(assignedTo);
                userStory.setIteration(iteration);
                userStory.setTeam(team);
                userStory.setEstimatedTime(dto.getEstimatedTime());
                userStory.setActualTime(dto.getActualTime());

                userStoryRepository.save(userStory);

                return "User story created successfully with number: " + nextNumber;

            } catch (DataIntegrityViolationException e) {
                // Retry if duplicate occurs
                attempts++;
                if (attempts >= maxRetries) {
                    throw new RuntimeException("Failed due to concurrent requests. Try again.");
                }
            }
        }

        throw new RuntimeException("Unexpected error occurred");
    }
}