package com.example.elitedriverbackend.services;


import com.example.elitedriverbackend.domain.dtos.AuthRequest;
import com.example.elitedriverbackend.domain.dtos.RegisterRequest;
import com.example.elitedriverbackend.domain.entity.User;
import com.example.elitedriverbackend.repositories.UserRepository;
import com.example.elitedriverbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        return jwtService.generateToken(user.getUsername());
    }

    public String login(AuthRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authManager.authenticate(auth);
        return jwtService.generateToken(request.getUsername());
    }
}