package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.dto.UsDTO;
import com.example.project.service.UserStoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}