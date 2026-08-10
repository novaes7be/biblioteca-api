package com.contatodireto.bibliotecaapi.controller;

import com.contatodireto.bibliotecaapi.security.JwtService;
import com.contatodireto.bibliotecaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthController {

    final UserService userService;
    final JwtService jwtService;
}
