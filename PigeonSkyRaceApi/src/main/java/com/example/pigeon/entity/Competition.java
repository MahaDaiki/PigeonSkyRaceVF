package com.example.pigeon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Competition {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la course ne peut pas être vide")
    private String nomCourse;


    @NotNull(message = "Les coordonnées GPS du point de lâcher ne peuvent pas être nulles")
    private double latitudeLacher;

    @NotNull(message = "Les coordonnées GPS du point de lâcher ne peuvent pas être nulles")
    private double longitudeLacher;

    @NotNull(message = "La date et l'heure de départ ne peuvent pas être nulles")
    private LocalDateTime dateHeureDepart;

    @NotNull(message = "La distance prévisionnelle ne peut pas être nulle")
    private double distancePrevisionnelle;

    private String season;


    private Boolean estTermine;


    @ManyToMany
    @JoinTable(
            name = "competition_pigeon",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "pigeon_id")
    )
    private List<Pigeon> pigeons;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Resultat> resultats;


    public Competition() {}

    public Competition(String nomCourse, double latitudeLacher, double longitudeLacher, LocalDateTime dateHeureDepart, double distancePrevisionnelle, String season, Boolean estTermine , List<Resultat> resultats) {
        this.nomCourse = nomCourse;
        this.latitudeLacher = latitudeLacher;
        this.longitudeLacher = longitudeLacher;
        this.dateHeureDepart = dateHeureDepart;
        this.distancePrevisionnelle = distancePrevisionnelle;
        this.season = season;
        this.estTermine = estTermine;
        this.resultats = resultats;
    }


}
