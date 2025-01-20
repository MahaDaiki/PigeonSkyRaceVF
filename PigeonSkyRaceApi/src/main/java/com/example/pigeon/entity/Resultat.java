package com.example.pigeon.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Duration;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "resultats")
public class Resultat {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "L'ID de la compétition ne peut pas être nul")
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;


    @NotNull(message = "Le numéro de bague ne peut pas être nul")
    @ManyToOne
    @JoinColumn(name = "pigeon_id", nullable = false)
    private Pigeon pigeon;

    private double distanceParcourue;

    private double vitesse;

    private Duration tempsParcourue;

    private LocalDateTime heureArrivee;
    private double point;

    private int classement;


    public Resultat() {}

    public Resultat(Competition competition, Pigeon pigeon, double distanceParcourue, double vitesse, Duration tempsParcourue, LocalDateTime heureArrivee, double point, int classement) {
        this.competition = competition;
        this.pigeon = pigeon;
        this.distanceParcourue = distanceParcourue;
        this.vitesse = vitesse;
        this.tempsParcourue = tempsParcourue;
        this.heureArrivee = heureArrivee;
        this.point = point;
        this.classement = classement;
    }



}
