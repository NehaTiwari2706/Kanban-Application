package com.example.project.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.MyWorkPageDTO;
import com.example.project.dto.TaskDTO;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.TaskService;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/my-work")
    public ResponseEntity<ApiResponse<MyWorkPageDTO>> getMyWork(
            Authentication authentication,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            String email = authentication != null ? authentication.getName() : null;
            if (email == null || email.isBlank()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Authentication required", null, null));
            }

            User currentUser = userRepository.findByEmail(email).orElse(null);
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Authenticated user not found", null, null));
            }

            MyWorkPageDTO data = taskService.getMyWorkTasks(
                    currentUser.getId(),
                    search,
                    status,
                    priority,
                    assigneeId,
                    page,
                    size
            );

            Map<String, Object> pagination = new LinkedHashMap<>();
            pagination.put("page", page);
            pagination.put("size", size);
            pagination.put("totalElements", data.getTotalElements());
            pagination.put("totalPages", data.getTotalPages());

            return ResponseEntity.ok(new ApiResponse<>(true, "Tasks fetched successfully", data, pagination));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to load my work tasks: " + ex.getMessage(), null, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task fetched successfully", task, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@RequestBody TaskDTO dto) {
        try {
            TaskDTO created = taskService.createTask(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Task created successfully", created, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null, null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable Long id, @RequestBody TaskDTO dto) {
        TaskDTO updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task updated successfully", updated, null));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        TaskDTO updated = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task status updated successfully", updated, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task deleted successfully", null, null));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserStoryId(@RequestParam Long userStoryId) {
        return ResponseEntity.ok(taskService.getAllTasksByUserStoryId(userStoryId));
    }
}
