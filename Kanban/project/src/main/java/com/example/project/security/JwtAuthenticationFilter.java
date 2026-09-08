package com.example.project.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
/** Authenticates each request that contains a valid Bearer JWT. */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Public requests and requests without a token continue to Spring Security's authorization rules.
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {
            // The token subject is the user's email, which is used to load the current database user.
            String email = jwtService.extractSubject(token);

            if (SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtService.isTokenValid(token, email)) {
                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {
                    // Store the authenticated identity so controllers and Spring Security can access it.
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, java.util.Collections.emptyList());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (RuntimeException exception) {
            // Invalid or tampered tokens must not authenticate the request.
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}