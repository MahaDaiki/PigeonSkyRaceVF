package com.example.pigeon.service.impl;

import com.example.pigeon.dto.CompetitionDto;
import com.example.pigeon.entity.Competition;
import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.repository.CompetitionRepository;
import com.example.pigeon.service.CompetitionService;
import com.example.pigeon.service.PigeonService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private PigeonService pigeonService;

    @Override
    public CompetitionDto addCompetition(CompetitionDto competitionDto) {
        List<Long> pigeonIds = competitionDto.getPigeonIds();
        if (pigeonIds != null && !pigeonIds.isEmpty()) {
            List<Pigeon> existingPigeons = pigeonService.getPigeonsByIds(pigeonIds);

            if (existingPigeons.size() != pigeonIds.size()) {
                throw new IllegalArgumentException("Un ou plusieurs IDs de pigeon sont invalides");
            }
            if (competitionDto.getLatitudeLacher() < -90 || competitionDto.getLatitudeLacher() > 90) {
                throw new IllegalArgumentException("Latitude doit être entre -90 et 90");
            }

            if (competitionDto.getLongitudeLacher() < -180 || competitionDto.getLongitudeLacher() > 180) {
                throw new IllegalArgumentException("Longitude doit être entre -180 et 180");
            }

             if (competitionDto.getDateHeureDepart().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("La date et l'heure de départ doivent être dans le futur");
            }

            if (competitionDto.getDistancePrevisionnelle() < 0) {
                throw new IllegalArgumentException("La distance prévisionnelle ne peut pas être négative");
            }

            Competition competition = competitionDto.toEntity(existingPigeons);

            Competition savedCompetition = competitionRepository.save(competition);
            return CompetitionDto.toDto(savedCompetition);
        } else {
            throw new IllegalArgumentException("La liste des IDs de pigeon est vide");
        }
    }

    @Override
    public CompetitionDto getCompetitionById(Long id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        return competition != null ? CompetitionDto.toDto(competition) : null;
    }


    @Override
    public CompetitionDto modifyStatus(Long id, Boolean estTermine) {
        try {
            Competition competition = competitionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));
            competition.setEstTermine(estTermine);
            Competition updatedCompetition = competitionRepository.save(competition);
            return CompetitionDto.toDto(updatedCompetition);
        } catch (Exception e) {
            System.err.println("Error modifying competition status: " + e.getMessage());
            return null;
        }
    }

}
