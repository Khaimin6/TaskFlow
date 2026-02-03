package com.example.taskflow.controller;

import com.example.taskflow.dto.AuthDtos;
import com.example.taskflow.model.User;
import com.example.taskflow.security.JwtUtil;
import com.example.taskflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthDtos.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        return ResponseEntity.ok(new AuthDtos.AuthResponse(jwt, loginRequest.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthDtos.RegisterRequest registerRequest) {
        if (userService.registerUser(
                User.builder()
                        .username(registerRequest.getUsername())
                        .password(registerRequest.getPassword())
                        .email(registerRequest.getEmail())
                        .build()) != null) {
            return ResponseEntity.ok("User registered successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }
}
