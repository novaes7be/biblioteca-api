package com.contatodireto.bibliotecaapi.service;

import com.contatodireto.bibliotecaapi.dto.AuthResponse;
import com.contatodireto.bibliotecaapi.dto.LoginRequest;
import com.contatodireto.bibliotecaapi.dto.RegisterRequest;
import com.contatodireto.bibliotecaapi.model.User;
import com.contatodireto.bibliotecaapi.repository.UserRepository;

import com.contatodireto.bibliotecaapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Service
@Setter
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
     UserRepository userRepository;
    @Mock
     UserService userService;
    @Mock
     PasswordEncoder passwordEncoder;
    @Mock
     JwtService jwtService;
    @InjectMocks
    private AuthService authService;


    User userTest;

    @BeforeEach
    void setup() {
        userTest = new User();
        userTest.setEmail("teste@email.com");
        userTest.setPassword("senhacriptografada");
        userTest.setName("Joao");
    }

    @Test
    void emailSuccessful() {
        LoginRequest request = new LoginRequest("teste@email.com", "senha123");

        when(passwordEncoder.matches(request.password(), userTest.getPassword())).thenReturn(true);
        when(userRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(userTest));
        when(jwtService.generateToken(request.email())).thenReturn("tokenFake123");

        //mockando todos os repositorios que o authresponse espera receber para fazer o .login()

        AuthResponse response = authService.login(request);
        assertEquals("tokenFake123" , response.token());
    }


    
    @Test
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
