package com.contatodireto.bibliotecaapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;


public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService,
                "secret",
                "bWluaGFDaGF2ZVNlY3JldGFTdXBlclNlZ3VyYVBhcmFUZXN0ZXNKV1Q=");
        ReflectionTestUtils.setField(jwtService,
                "expiration"
                , 3600000L);
    }

    @Test
    void generateTokenWithEmailAsSubject() {
        String token = jwtService.generateToken("bernardo@contatodireto.com");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractEmailFromToken() {
        String token = jwtService.generateToken("bernardo@contatodireto.com");

        String emailExtracted = jwtService.extractEmail(token);

        assertEquals("bernardo@contatodireto.com", emailExtracted);
    }

    @Test
    void isTokenValid () {
        String token = jwtService.generateToken("bernardo@contatodireto.com");
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenModified () {
        String token = jwtService.generateToken("bernardo@contatodireto.com");

        String tokenModificado = token + "modified";

        assertFalse(jwtService.isTokenValid(tokenModificado));
    }

    @Test
    void isTokenExpired () throws InterruptedException {
        ReflectionTestUtils.setField(jwtService, "expiration", 1L);
        String token = jwtService.generateToken("bernardo@contatodireto.com");

        Thread.sleep(50);

        assertFalse(jwtService.isTokenValid(token));
    }
}

