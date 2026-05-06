package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.IterationDTO;
import com.example.project.dto.UsDTO;
import com.example.project.service.UserStoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController 
@RequestMapping("/api/user-stories")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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
    public ResponseEntity<List<UsDTO>> getAllUserStoriesbyIteration(@RequestParam Long iterationId) {
        return ResponseEntity.ok(userStoryService.getAllUserStoriesByIterationID(iterationId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserStory(@PathVariable Long id) {
        return ResponseEntity.ok(userStoryService.deleteUserStory(id));
    }

}