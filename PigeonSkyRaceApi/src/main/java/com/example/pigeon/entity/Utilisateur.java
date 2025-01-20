package com.example.pigeon.entity;


import com.example.pigeon.dto.UtilisateurDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Builder
@AllArgsConstructor

@Entity
public class Utilisateur {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Column(nullable = false, unique = true)
    private String username;



    @NotNull(message = "Le mot de passe ne peut pas être nul")
    @Column(nullable = false)
    private String motDePasse;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    private double latitude;

    private double longitude;

    @Enumerated(EnumType.STRING)
//    @NotBlank(message = "Ou est le roooole ?????")
    private Role role;


    @OneToMany(mappedBy = "eleveur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pigeon> pigeons;

    public Utilisateur() {}

    public Utilisateur( String username, String motDePasse, String nom, double latitude, double longitude, Role role, List<Pigeon> pigeons) {
        this.username = username;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = role;
        this.pigeons = pigeons;
    }


}
