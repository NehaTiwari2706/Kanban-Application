package com.example.project.controller;

import com.example.project.dto.UsDTO;
import com.example.project.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-stories")
public class UserStoryController {

    @Autowired
    private UserStoryService userStoryService;

    // Endpoint to create a user story
    // POST /api/user-stories/create
    @PostMapping("/create")
    public String createUserStory(@RequestBody UsDTO dto) {
        return userStoryService.createUserStory(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsDTO>> getAllUserStoriesByIteration(@RequestParam Long iterationId) {
        return ResponseEntity.ok(userStoryService.getAllUserStoriesByIterationID(iterationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserStory(@PathVariable Long id) {
        return ResponseEntity.ok(userStoryService.deleteUserStory(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserStory(@PathVariable Long id, @RequestBody UsDTO dto) {
        return ResponseEntity.ok(userStoryService.updateUserStory(id, dto));
    }
}