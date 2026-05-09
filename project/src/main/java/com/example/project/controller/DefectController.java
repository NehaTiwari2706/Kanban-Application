package com.example.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.project.dto.DefectDTO;
import com.example.project.service.DefectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/defect")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DefectController {
   
    @Autowired
    DefectService defectService;
    
    @PostMapping("/create")
    public ResponseEntity<String> createDefect(@RequestBody DefectDTO dto){
        return ResponseEntity.ok(defectService.createDefect(dto));
    }

    @GetMapping
    public ResponseEntity<List<DefectDTO>> getDefects(
            @RequestParam(required = false) Long iterationId,
            @RequestParam(required = false) Long userStoryId) {

        return ResponseEntity.ok(
                defectService.getDefects(iterationId, userStoryId));
    }

    
}
