package com.example.project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iterations")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class IterationController {
    
}
