package com.example.pigeon.controller;


import com.example.pigeon.dto.CompetitionDto;
import com.example.pigeon.dto.CompetitionRequestDto;
import com.example.pigeon.entity.Competition;
import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.entity.Role;
import com.example.pigeon.service.CompetitionService;
import com.example.pigeon.service.PigeonService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/competition")
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;
    @Autowired
    private PigeonService pigeonService;


    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addCompetition(@RequestBody CompetitionDto competitionDto) {
        System.out.println("Received CompetitionDto: " + competitionDto);
        List<Long> pigeonIds = competitionDto.getPigeonIds();
        System.out.println("Pigeon IDs requested: " + pigeonIds);


        if (pigeonIds == null || pigeonIds.isEmpty()) {
            return ResponseEntity.badRequest().body("La liste des IDs de pigeon est vide");
        }

        try {
            CompetitionDto savedCompetitionDto = competitionService.addCompetition(competitionDto);
            return ResponseEntity.ok("Compétition ajoutée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDto> getCompetitionById(@PathVariable Long id) {
        CompetitionDto competitionDto = competitionService.getCompetitionById(id);
        if (competitionDto != null) {
            return ResponseEntity.ok(competitionDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> modifyStatus(
            @PathVariable Long id,
            @RequestBody CompetitionRequestDto requestDto) {
        System.out.println("Received request to update status for competition ID: " + id + " with estTermine: " + requestDto.getEstTermine());

        Boolean estTermine = requestDto.getEstTermine();
        System.out.println("Updating status for competition ID: " + id + " to estTermine: " + estTermine);

        CompetitionDto updatedCompetition = competitionService.modifyStatus(id, estTermine);
        if (updatedCompetition != null) {
            return ResponseEntity.ok("Statut de la compétition mis à jour avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compétition non trouvée");
        }
    }


}












