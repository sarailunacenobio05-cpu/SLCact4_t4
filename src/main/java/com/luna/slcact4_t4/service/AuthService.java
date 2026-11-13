package com.luna.slcact4_t4.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luna.slcact4_t4.dto.AuthResponse;
import com.luna.slcact4_t4.dto.LoginRequest;
import com.luna.slcact4_t4.dto.RegisterRequest;
import com.luna.slcact4_t4.dto.UserResponse;
import com.luna.slcact4_t4.entity.AppUser;
import com.luna.slcact4_t4.entity.Role;
import com.luna.slcact4_t4.exception.ConflictException;
import com.luna.slcact4_t4.repository.AppUserRepository;
import com.luna.slcact4_t4.security.JwtService;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AppUserRepository users, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new ConflictException("Ya existe un usuario registrado con ese email");
        }
        AppUser u = new AppUser();
        u.setName(req.name().trim());
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setRole(Role.USER);
        u = users.save(u);
        return buildResponse(u);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        String email = req.email().trim().toLowerCase();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, req.password()));
        AppUser u = users.findByEmail(email).orElseThrow();
        return buildResponse(u);
    }

    private AuthResponse buildResponse(AppUser u) {
        String token = jwtService.generateToken(u.getEmail(), u.getRole().name());
        return new AuthResponse(token, "Bearer", jwtService.getExpirationSeconds(), UserResponse.from(u));
    }
}
