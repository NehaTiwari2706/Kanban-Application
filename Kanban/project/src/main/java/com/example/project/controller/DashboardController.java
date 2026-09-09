package com.example.project.controller;

import com.example.project.dto.DashboardDTO;
import com.example.project.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard(Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(dashboardService.getDashboard(principal.getName()));
    }
}
