package com.example.project.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.RoleDTO;
import com.example.project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.project.service.RoleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class RolesController {
    
    @Autowired
    private RoleService roleService;
    
     @GetMapping
     public List<RoleDTO> getAllRoles() { 
            return (List<RoleDTO>) roleService.findAll();
     }

     @PostMapping
     public RoleDTO createRole(@RequestBody RoleDTO role) { 
            return roleService.createRole(role);
     }

     //Assign Role API
    @PostMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<String> assignRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {

        String response = roleService.assignRole(userId, roleId);

        return ResponseEntity.ok(response);
    }
     

}      
