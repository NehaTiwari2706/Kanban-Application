package com.example.project.service;

import com.example.project.dto.UserRegisterRequest;
import com.example.project.dto.UserLoginRequest;
import com.example.project.dto.AuthResponse;
import com.example.project.dto.UserDTO;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse register(UserRegisterRequest request) {
        // validate input
        if(request.getFullName() == null || request.getFullName().trim().isEmpty()){
            return new AuthResponse(false, "Full name is required");
        }
        if(request.getEmail() == null || request.getEmail().trim().isEmpty()){
            return new AuthResponse(false, "Email is required");
        }
        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            return new AuthResponse(false, "Password is required");
        }
        if(!request.getPassword().equals(request.getConfirmPassword())){
            return new AuthResponse(false, "Passwords do not match");
        }
        if(request.getDomain() == null || request.getDomain().trim().isEmpty()){
            return new AuthResponse(false, "Domain is required");
        }
         // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, "Email already registered");
        }

        try {
            User user = new User();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            //Encrpt password using BCrypt
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setDomain(request.getDomain());

            //save user to database
            User savedUser = userRepository.save(user);

            //Return success response with user details (excluding password)
            UserDTO userDTO = new UserDTO(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getDomain()
            );
            return new AuthResponse(true, "User registered successfully", userDTO);
        } catch (Exception e) {
            return new AuthResponse(false, "Registration failed: " + e.getMessage());
        }
    }

    public AuthResponse login(UserLoginRequest request) {
        // validate input
        if(request.getEmail() == null || request.getEmail().trim().isEmpty()){
            return new AuthResponse(false, "Email is required");
        }
        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            return new AuthResponse(false, "Password is required");
        }

        try {
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if(userOpt.isEmpty()){
                return new AuthResponse(false, "Invalid email or password");
            }
            User user = userOpt.get();
            // check password
            if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
                return new AuthResponse(false, "Invalid email or password");
            }

            // Return success response with user details (excluding password)
            UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDomain()
            );
            return new AuthResponse(true, "Login successful", userDTO);
        } catch (Exception e) {
            return new AuthResponse(false, "Login failed: " + e.getMessage());
        }
    }
}