package com.contatodireto.bibliotecaapi.service;

import com.contatodireto.bibliotecaapi.dto.AuthResponse;
import com.contatodireto.bibliotecaapi.dto.LoginRequest;
import com.contatodireto.bibliotecaapi.dto.RegisterRequest;
import com.contatodireto.bibliotecaapi.model.User;
import com.contatodireto.bibliotecaapi.repository.UserRepository;

import com.contatodireto.bibliotecaapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class AuthService {

    final UserRepository userRepository;
    final UserService userService;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new BadCredentialsException("Credentials invalid"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Password invalid");
        }
        String token = jwtService.generateToken(request.email());
        return new AuthResponse(token);
    }
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        userService.register(user);
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
