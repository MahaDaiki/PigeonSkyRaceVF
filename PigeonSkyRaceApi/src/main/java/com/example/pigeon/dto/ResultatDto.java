package com.example.pigeon.dto;

import com.example.pigeon.entity.Resultat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResultatDto {

        private long id;
        private long competitionId;
        private long pigeonId;
        private double distanceParcourue;
        private double vitesse;
        private Duration tempsParcourue;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
        private LocalDateTime heureArrivee;
        private double point;
        private int classement;


    public static ResultatDto toDto(Resultat entity) {
        return ResultatDto.builder()
                .id(entity.getId())
                .competitionId(entity.getCompetition().getId())
                .pigeonId(entity.getPigeon().getId())
                .distanceParcourue(entity.getDistanceParcourue())
                .vitesse(entity.getVitesse())
                .tempsParcourue(entity.getTempsParcourue())
                .heureArrivee(entity.getHeureArrivee())
                .point(entity.getPoint())
                .classement(entity.getClassement())
                .build();
    }


    public Resultat toEntity() {
        Resultat resultat = new Resultat();
        resultat.setId(this.id);
        resultat.setDistanceParcourue(this.distanceParcourue);
        resultat.setVitesse(this.vitesse);
        resultat.setTempsParcourue(this.tempsParcourue);
        resultat.setHeureArrivee(this.heureArrivee);
        resultat.setPoint(this.point);
        resultat.setClassement(this.classement);
        return resultat;
    }

}
