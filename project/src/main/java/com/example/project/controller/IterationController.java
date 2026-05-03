package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.dto.IterationDTO;
import com.example.project.service.IterationService;

@RestController
@RequestMapping("/api/iterations")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class IterationController {

    @Autowired
    private IterationService IterationService;
    
    @PostMapping("/create")
    public ResponseEntity<String> CreateIteration(@RequestBody IterationDTO iterationRequest) {
        // Logic to create an iteration
        try{
            String response =  IterationService.createIteration(iterationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response); 
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
    }
    }
}
