package com.example.project.service;

import com.example.project.dto.DashboardDTO;
import com.example.project.entity.Iteration;
import com.example.project.entity.Task;
import com.example.project.entity.User;
import com.example.project.repository.DefectRepository;
import com.example.project.repository.IterationRepository;
import com.example.project.repository.TaskRepository;
import com.example.project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final DefectRepository defectRepository;
    private final IterationRepository iterationRepository;

    public DashboardService(UserRepository userRepository,
                            TaskRepository taskRepository,
                            DefectRepository defectRepository,
                            IterationRepository iterationRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.defectRepository = defectRepository;
        this.iterationRepository = iterationRepository;
    }

    public DashboardDTO getDashboard(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated email missing");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated user not found"));

        long totalTasks = taskRepository.count();
        long inProgress = taskRepository.countByStatus(Task.Status.IN_PROGRESS);
        long completed = taskRepository.countByStatus(Task.Status.DONE);
        long defects = defectRepository.count();

        DashboardDTO dto = new DashboardDTO();
        dto.setUser(new DashboardDTO.UserSummaryDTO(user.getId(), user.getFullName()));
        dto.setSummary(new DashboardDTO.DashboardSummaryDTO(totalTasks, inProgress, completed, defects));

        List<Iteration> currentIterations = iterationRepository.findCurrentIteration(LocalDate.now());
        if (!currentIterations.isEmpty()) {
            Iteration currentIteration = currentIterations.get(0);
            long totalInIteration = taskRepository.countByIterationId(currentIteration.getId());
            long doneInIteration = taskRepository.countByIterationIdAndStatus(currentIteration.getId(), Task.Status.DONE);
            int progress = totalInIteration == 0 ? 0 : (int) Math.round((doneInIteration * 100.0) / totalInIteration);

            dto.setCurrentIteration(new DashboardDTO.IterationSummaryDTO(
                    currentIteration.getId(),
                    currentIteration.getName(),
                    currentIteration.getStartDate(),
                    currentIteration.getEndDate(),
                    progress
            ));
        }

        List<Task> assignedTasks = taskRepository.findByAssignedToId(user.getId());
        List<DashboardDTO.TaskSummaryDTO> myWork = assignedTasks.stream()
                .limit(10)
                .map(task -> new DashboardDTO.TaskSummaryDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getStatus().name(),
                        task.getPriority().name()
                ))
                .collect(Collectors.toList());

        dto.setMyWork(myWork);
        return dto;
    }
}
