package com.example.project.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.MyWorkPageDTO;
import com.example.project.dto.TaskCardDTO;
import com.example.project.dto.TaskDTO;
import com.example.project.dto.UserSummaryDTO;
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

    public MyWorkPageDTO getMyWorkTasks(
            Long currentUserId,
            String search,
            String status,
            String priority,
            Long assigneeId,
            int page,
            int size) {

        List<Task> tasks = taskRepository.findByAssignedToId(currentUserId);
        if (tasks == null) {
            tasks = List.of();
        }

        if (assigneeId != null) {
            tasks = tasks.stream()
                    .filter(task -> task != null && task.getAssignedTo() != null && Objects.equals(task.getAssignedTo().getId(), assigneeId))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.isBlank()) {
            tasks = tasks.stream()
                    .filter(task -> task != null && task.getStatus() != null && task.getStatus().name().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        if (priority != null && !priority.isBlank()) {
            tasks = tasks.stream()
                    .filter(task -> task != null && task.getPriority() != null && task.getPriority().name().equalsIgnoreCase(priority))
                    .collect(Collectors.toList());
        }

        if (search != null && !search.isBlank()) {
            String needle = search.trim().toLowerCase();
            tasks = tasks.stream()
                    .filter(task -> task != null && task.getTitle() != null && task.getTitle().toLowerCase().contains(needle))
                    .collect(Collectors.toList());
        }

        tasks.sort(Comparator.comparing(Task::getId).reversed());

        MyWorkPageDTO result = new MyWorkPageDTO();
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }
            TaskCardDTO dto = convertToCardDTO(task);
            if (task.getStatus() == null) {
                continue;
            }
            switch (task.getStatus()) {
                case TODO -> result.getTodo().add(dto);
                case IN_PROGRESS -> result.getInProgress().add(dto);
                case DONE -> result.getDone().add(dto);
                default -> {
                }
            }
        }

        int start = Math.min(page * size, tasks.size());
        int end = Math.min(start + size, tasks.size());
        List<Task> pageItems = tasks.subList(start, end);

        MyWorkPageDTO paged = new MyWorkPageDTO();
        for (Task task : pageItems) {
            if (task == null) {
                continue;
            }
            TaskCardDTO dto = convertToCardDTO(task);
            if (task.getStatus() == null) {
                continue;
            }
            switch (task.getStatus()) {
                case TODO -> paged.getTodo().add(dto);
                case IN_PROGRESS -> paged.getInProgress().add(dto);
                case DONE -> paged.getDone().add(dto);
                default -> {
                }
            }
        }

        paged.setTotalElements(tasks.size());
        paged.setTotalPages(size <= 0 ? 1 : (int) Math.ceil((double) tasks.size() / size));
        return paged;
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToDTO(task);
    }

    @Transactional
    public TaskDTO createTask(TaskDTO dto) {
        Long resolvedUserStoryId = dto.getUserStoryId() != null ? dto.getUserStoryId() : dto.getProjectId();
        if (resolvedUserStoryId == null) {
            resolvedUserStoryId = userStoryRepository.findAll().stream()
                    .findFirst()
                    .map(UserStory::getId)
                    .orElseThrow(() -> new RuntimeException("User story not found. Please provide a valid userStoryId."));
        }

        UserStory userStory = userStoryRepository.findById(resolvedUserStoryId)
                .orElseThrow(() -> new RuntimeException("User story not found"));

        Long assignedToId = dto.getAssignedToId() != null ? dto.getAssignedToId() : dto.getAssigneeId();
        User assignedTo = null;
        if (assignedToId != null) {
            assignedTo = userRepository.findById(assignedToId)
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        User createdBy = dto.getCreatedById() != null
                ? userRepository.findById(dto.getCreatedById()).orElseThrow(() -> new RuntimeException("Created by user not found"))
                : assignedTo != null ? assignedTo : userRepository.findById(userStory.getCreatedBy().getId()).orElseThrow(() -> new RuntimeException("Created by user not found"));

        Task.Status status = Task.Status.valueOf(dto.getStatus().toUpperCase());
        Task.Priority priority = Task.Priority.valueOf(dto.getPriority().toUpperCase());

        int attempts = 0;
        int maxRetries = 3;
        while (attempts < maxRetries) {
            try {
                int maxTask = taskRepository.findMaxTaskNumberByUserStoryId(dto.getUserStoryId());
                int nextTaskNumber = maxTask + 1;

                Task task = new Task();
                task.setTaskNumber(nextTaskNumber);
                task.setTitle(dto.getTitle());
                task.setDescription(dto.getDescription() == null ? "" : dto.getDescription());
                task.setStatus(status);
                task.setPriority(priority);
                task.setEstimatedTime(dto.getEstimatedTime() == null ? 0 : dto.getEstimatedTime());
                task.setActualTime(dto.getActualTime() == null ? 0 : dto.getActualTime());
                task.setUserStory(userStory);
                task.setAssignedTo(assignedTo);
                task.setCreatedBy(createdBy);
                taskRepository.save(task);
                return convertToDTO(task);
            } catch (DataIntegrityViolationException e) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw new RuntimeException("Failed due to concurrent requests. Try again.");
                }
            }
        }

        throw new RuntimeException("Unexpected error occurred");
    }

    public List<TaskDTO> getAllTasksByUserStoryId(Long userStoryId) {
        return taskRepository.findByUserStoryId(userStoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            task.setStatus(Task.Status.valueOf(dto.getStatus().toUpperCase()));
        }
        if (dto.getPriority() != null && !dto.getPriority().isBlank()) {
            task.setPriority(Task.Priority.valueOf(dto.getPriority().toUpperCase()));
        }
        if (dto.getEstimatedTime() != null) {
            task.setEstimatedTime(dto.getEstimatedTime());
        }
        if (dto.getActualTime() != null) {
            task.setActualTime(dto.getActualTime());
        }

        if (dto.getAssignedToId() != null) {
            User assignedTo = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAssignedTo(assignedTo);
        }

        if (dto.getUserStoryId() != null && !dto.getUserStoryId().equals(task.getUserStory().getId())) {
            UserStory newUserStory = userStoryRepository.findById(dto.getUserStoryId())
                    .orElseThrow(() -> new RuntimeException("User story not found"));
            task.setUserStory(newUserStory);
        }

        taskRepository.save(task);
        return convertToDTO(task);
    }

    @Transactional
    public TaskDTO updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (status == null || status.isBlank()) {
            throw new RuntimeException("Status is required");
        }
        task.setStatus(Task.Status.valueOf(status.toUpperCase()));
        taskRepository.save(task);
        return convertToDTO(task);
    }

    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }

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
        dto.setUserStoryId(task.getUserStory() != null ? task.getUserStory().getId() : null);
        dto.setAssignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
        dto.setCreatedById(task.getCreatedBy() != null ? task.getCreatedBy().getId() : null);
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }

    private TaskCardDTO convertToCardDTO(Task task) {
        TaskCardDTO dto = new TaskCardDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setPriority(task.getPriority() != null ? task.getPriority().name() : "MEDIUM");
        dto.setStatus(task.getStatus() != null ? task.getStatus().name() : "TODO");
        dto.setCreatedAt(task.getCreatedAt());

        if (task.getAssignedTo() != null) {
            UserSummaryDTO assignee = new UserSummaryDTO();
            assignee.setId(task.getAssignedTo().getId());
            assignee.setName(task.getAssignedTo().getFullName());
            assignee.setInitials(getInitials(task.getAssignedTo().getFullName()));
            dto.setAssignee(assignee);
        }

        return dto;
    }

    private String getInitials(String name) {
        if (name == null || name.isBlank()) {
            return "U";
        }
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }
}