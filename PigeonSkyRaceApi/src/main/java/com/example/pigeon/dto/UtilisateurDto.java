package com.example.pigeon.dto;

import com.example.pigeon.entity.Role;
import com.example.pigeon.entity.Utilisateur;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide.")
    private String username;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    private String nom;

    private double latitude;
    private double longitude;

    @NotNull(message = "Le mot de passe ne peut pas être vide.")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
//    @NotNull(message = "Le rôle ne peut pas être vide.")
    private Role role;

    public static UtilisateurDto toDto(Utilisateur entity) {
        return UtilisateurDto.builder()
                .username(entity.getUsername())
                .nom(entity.getNom())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .role(entity.getRole())
                .build();
    }

    public Utilisateur toEntity() {
        return Utilisateur.builder()
                .username(this.username)
                .nom(this.nom)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .motDePasse(this.motDePasse)
                .role(this.role)
                .build();
    }
}
