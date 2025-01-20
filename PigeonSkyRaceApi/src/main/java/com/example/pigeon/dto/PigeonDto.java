package com.example.pigeon.dto;

import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.entity.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PigeonDto {
    private long  id;


    @NotBlank(message = " Le numéro de bague ne peut pas être vide")
    @Pattern(regexp = "^[mf].*", message = "Le numéro de bague doit commencer par 'm' pour mâle ou 'f' pour femelle")
    private String numeroBague;

    @NotBlank(message = "La couleur ne peut pas être vide")
    private String couleur;

    @NotNull(message = "L'age ne peut pas être vide")
    private int age;

    @NotNull(message = "L'ID de l'éleveur ne peut pas être nul")
    @JsonIgnore
    private Long eleveurId;




    public static PigeonDto toDto(Pigeon pigeon) {
        PigeonDto pigeonDto = new PigeonDto();
        pigeonDto.setId(pigeon.getId());
        pigeonDto.setNumeroBague(pigeon.getNumeroBague());
        pigeonDto.setCouleur(pigeon.getCouleur());
        pigeonDto.setAge(pigeon.getAge());
        pigeonDto.setEleveurId(pigeon.getEleveur().getId());
        return pigeonDto;
    }


    public Pigeon toEntity() {
        Pigeon pigeon = new Pigeon();
        pigeon.setId(this.id);
        pigeon.setNumeroBague(this.numeroBague);
        pigeon.setCouleur(this.couleur);
        pigeon.setAge(this.age);
        return pigeon;
    }
}
