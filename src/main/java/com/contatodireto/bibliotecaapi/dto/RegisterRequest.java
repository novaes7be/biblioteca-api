package com.contatodireto.bibliotecaapi.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Setter;

public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password) {
}
