package com.example.pigeon.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Entity
@Table(name = "pigeons")
public class Pigeon {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Le numéro de bague ne peut pas être vide")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[mf].*", message = "Le numéro de bague doit commencer par 'm' pour mâle ou 'f' pour femelle")
    private String numeroBague;

    @NotBlank(message = "La couleur ne peut pas être vide")
    private String couleur;


    @NotNull(message = "L'age ne peut pas être vide")
    private int age;


    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnore
    private Utilisateur eleveur;

    @OneToMany(mappedBy = "pigeon", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Resultat> resultats;


    public Pigeon() {}

    public Pigeon(String numeroBague, String couleur, int age, Utilisateur eleveur, List<Resultat> resultats) {
        this.numeroBague = numeroBague;
        this.couleur = couleur;
        this.age = age;
        this.eleveur = eleveur;
        this.resultats = resultats;


    }



}
