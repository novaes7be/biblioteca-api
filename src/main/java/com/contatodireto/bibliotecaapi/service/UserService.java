package com.contatodireto.bibliotecaapi.service;

import com.contatodireto.bibliotecaapi.model.Role;
import com.contatodireto.bibliotecaapi.model.User;
import com.contatodireto.bibliotecaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public User register(User user) {

            Optional<User> userEmail = userRepository.findByEmail(user.getEmail());

        if (userEmail.isPresent()) {
            throw new RuntimeException("Email is already been used by another account. Try another one");
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
