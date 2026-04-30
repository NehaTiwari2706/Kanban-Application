    package com.example.project.controller;

    import com.example.project.service.AuthService;
    import com.example.project.dto.UserLoginRequest;
    import com.example.project.dto.UserRegisterRequest;
    import com.example.project.dto.AuthResponse;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import jakarta.servlet.http.HttpSession;

    @RestController
    @RequestMapping("/api/auth")
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    public class AuthController {

        @Autowired
        private AuthService authService;

        /**
         * Register a new user
         * POST /api/auth/register
         */
        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterRequest request){
            AuthResponse response = authService.register(request);
            if(response.isSuccess()){
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        /**
         * Login user
         * POST /api/auth/login
         */
        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest request, HttpSession session){
            AuthResponse response = authService.login(request);
            System.out.println("Session ID: " + session.getId());
            System.out.println("User: " + session.getAttribute("user"));    
            if(response.isSuccess()){
                session.setAttribute("user", response.getUser());
                session.setAttribute("userId", response.getUser().getId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(HttpSession session){
            session.invalidate(); // destroys session
            return ResponseEntity.ok("Logged out successfully");
        }
    }