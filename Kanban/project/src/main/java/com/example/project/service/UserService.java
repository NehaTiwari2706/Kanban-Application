package com.example.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.UserDTO;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Convert Entity → DTO
    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDomain()
        );
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {

            existingUser.setFullName(userDTO.getFullName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setDomain(userDTO.getDomain());

            User updated = userRepository.save(existingUser);

            return mapToDTO(updated);

        }).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}