package com.example.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;      
import org.springframework.stereotype.Service;
import com.example.project.dto.RoleDTO;
import com.example.project.entity.Role;
import com.example.project.entity.UserRole;
import com.example.project.entity.User;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserRoleRepository;


@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    // Convert Entity → DTO
    private RoleDTO mapToDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName()
        );
    }

    public List<RoleDTO> findAll(){
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public RoleDTO createRole(RoleDTO roledto){
        Role role = new Role();
        role.setName(roledto.getName());
        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }
    
    public String assignRole(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        //  prevent duplicate
        if (userRoleRepository.existsByUserAndRole(user, role)) {
            return "Role already assigned";
        }

        UserRole userRole = new UserRole(user, role);

        userRoleRepository.save(userRole);

        return "Role assigned successfully";
    }
}
