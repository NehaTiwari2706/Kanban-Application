    package com.example.project.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import com.example.project.dto.AuthResponse;
    import com.example.project.dto.UserLoginRequest;
import com.example.project.dto.UserRegisterRequest;
import com.example.project.security.JwtService;
import com.example.project.service.AuthService;


    @RestController
    @RequestMapping("/api/auth")
    /** Exposes registration, login, and logout endpoints. */
    public class AuthController {

        @Autowired
        private AuthService authService;

        @Autowired
        private JwtService jwtService;

        /**
         * Register a new user
         * POST /api/auth/register
         */
        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterRequest request){
            // Registration creates the account; login is required before a JWT is issued.
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
        public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest request){
            AuthResponse response = authService.login(request);
            if(response.isSuccess()){
            // Issue a stateless JWT. The Angular interceptor sends this token on later API requests.
                response.setToken(jwtService.generateToken(response.getUser().getEmail()));
            // Previously, the server stored the user and user ID in HttpSession here.
            // That stateful flow is commented out because JWT now carries the identity.
            // session.setAttribute("user", response.getUser());
            // session.setAttribute("userId", response.getUser().getId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(){
            // Previously, logout destroyed the server-side HttpSession.
            // JWT logout is handled by removing the token on the Angular client.
            // session.invalidate();
            return ResponseEntity.ok("Logged out successfully");
        }
    }