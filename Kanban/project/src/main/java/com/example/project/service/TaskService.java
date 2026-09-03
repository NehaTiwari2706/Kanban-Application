package com.example.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.TaskDTO;
import com.example.project.entity.Task;
import com.example.project.entity.User;
import com.example.project.entity.UserStory;
import com.example.project.repository.TaskRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserStoryRepository;

@Service
public class TaskService {

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    // CREATE TASK
    @Transactional
    public String createTask(TaskDTO dto) {

        // Fetch User Story
        UserStory userStory = userStoryRepository.findById(dto.getUserStoryId())
                .orElseThrow(() -> new RuntimeException("User story not found"));

        // Fetch Assigned User (optional)
        User assignedTo = null;
        if (dto.getAssignedToId() != null) {
            assignedTo = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        // Fetch Created By User
        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Created by user not found"));

        // Validate Status
        Task.Status status;
        try {
            status = Task.Status.valueOf(dto.getStatus().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid status. Use TODO, IN_PROGRESS, DONE");
        }

        // Validate Priority
        Task.Priority priority;
        try {
            priority = Task.Priority.valueOf(dto.getPriority().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid priority. Use LOW, MEDIUM, HIGH, CRITICAL");
        }

        // Retry mechanism for duplicate task number
        int attempts = 0;
        int maxRetries = 3;

        while (attempts < maxRetries) {
            try {
                int maxTask = taskRepository
                        .findMaxTaskNumberByUserStoryId(dto.getUserStoryId());

                int nextTaskNumber = maxTask + 1;

                Task task = new Task();
                task.setTaskNumber(nextTaskNumber);
                task.setTitle(dto.getTitle());
                task.setDescription(dto.getDescription());
                task.setStatus(status);
                task.setPriority(priority);
                task.setEstimatedTime(dto.getEstimatedTime());
                task.setActualTime(dto.getActualTime());
                task.setUserStory(userStory);
                task.setAssignedTo(assignedTo);
                task.setCreatedBy(createdBy);

                taskRepository.save(task);

                return "Task created successfully with number: " + nextTaskNumber;

            } catch (DataIntegrityViolationException e) {
                attempts++;

                if (attempts >= maxRetries) {
                    throw new RuntimeException(
                            "Failed due to concurrent requests. Try again.");
                }
            }
        }

        throw new RuntimeException("Unexpected error occurred");
    }

    // GET ALL TASKS BY USER STORY
    public List<TaskDTO> getAllTasksByUserStoryId(Long userStoryId) {

        List<Task> tasks = taskRepository.findByUserStoryId(userStoryId);

        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ENTITY -> DTO
    private TaskDTO convertToDTO(Task task) {

        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTaskNumber(task.getTaskNumber());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setPriority(task.getPriority().name());
        dto.setEstimatedTime(task.getEstimatedTime());
        dto.setActualTime(task.getActualTime());
        dto.setUserStoryId(task.getUserStory().getId());

        if (task.getAssignedTo() != null) {
            dto.setAssignedToId(task.getAssignedTo().getId());
        }

        if (task.getCreatedBy() != null) {
            dto.setCreatedById(task.getCreatedBy().getId());
        }

        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        return dto;
    }

    // DELETE TASK
    public String deleteTask(Long taskId) {

        if (!taskRepository.existsById(taskId)) {
            return "Task not found";
        }

        taskRepository.deleteById(taskId);

        return "Task deleted successfully";
    }

    // UPDATE TASK
    public String updateTask(Long id, TaskDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        // Validate Status
        Task.Status status;
        try {
            status = Task.Status.valueOf(dto.getStatus().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid status. Use TODO, IN_PROGRESS, DONE");
        }

        // Validate Priority
        Task.Priority priority;
        try {
            priority = Task.Priority.valueOf(dto.getPriority().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid priority. Use LOW, MEDIUM, HIGH, CRITICAL");
        }

        task.setStatus(status);
        task.setPriority(priority);

        task.setEstimatedTime(dto.getEstimatedTime());
        task.setActualTime(dto.getActualTime());

        // Update assigned user
        if (dto.getAssignedToId() != null) {
            User assignedTo = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            task.setAssignedTo(assignedTo);
        }

        // Update user story if changed
        if (dto.getUserStoryId() != null
                && !dto.getUserStoryId().equals(task.getUserStory().getId())) {

            UserStory newUserStory = userStoryRepository
                    .findById(dto.getUserStoryId())
                    .orElseThrow(() -> new RuntimeException("User story not found"));

            int maxTask = taskRepository
                    .findMaxTaskNumberByUserStoryId(dto.getUserStoryId());

            task.setTaskNumber(maxTask + 1);
            task.setUserStory(newUserStory);
        }

        taskRepository.save(task);

        return "Task updated successfully";
    }
}