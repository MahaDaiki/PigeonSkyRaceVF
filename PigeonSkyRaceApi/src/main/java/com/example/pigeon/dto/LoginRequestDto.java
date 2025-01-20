package com.example.pigeon.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "Le nom d'utilisateur ne doit pas être vide.")
    private String username;

    @NotBlank(message = "Le mot de passe ne doit pas être vide.")
    private String motDePasse;
}
