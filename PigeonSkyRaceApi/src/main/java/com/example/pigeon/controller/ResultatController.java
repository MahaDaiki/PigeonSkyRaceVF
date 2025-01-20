package com.example.pigeon.controller;

import com.example.pigeon.dto.CompetitionDto;
import com.example.pigeon.dto.CompetitionRequestDto;
import com.example.pigeon.dto.ResultatDto;
import com.example.pigeon.entity.Role;
import com.example.pigeon.exception.ResourceNotFoundException;
import com.example.pigeon.service.CalculService;
import com.example.pigeon.service.ResultatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/resultats")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private CalculService calculService;


//    @PostMapping("/{competitionId}")
//    public ResponseEntity<List<ResultatDto>> createResultsForCompetition(
//            @PathVariable String competitionId,
//            HttpSession session,
//            @RequestBody List<ResultatDto> resultatDtos) {
//
//        String userId = (String) session.getAttribute("utilisateurId");
//        if (userId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        Role role = (Role) session.getAttribute("utilisateurRole");
//        if (role != Role.organisateur) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        System.out.println(resultatDtos + " results ");
//        List<ResultatDto> resultats = new ArrayList<>();
//
//        for (ResultatDto resultatDto : resultatDtos) {
//            List<ResultatDto> createdResults = resultatService.createResultsForCompetition(competitionId,  resultatDtos);
//            resultats.addAll(createdResults);
//        }
//
//        if (resultats.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return new ResponseEntity<>(resultats, HttpStatus.CREATED);
//    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PostMapping("/{competitionId}/uploadResults")
    public String uploadResultsFile(@PathVariable Long competitionId, @RequestParam("file") MultipartFile file) {
        try {
            resultatService.uploadResultsFile(competitionId, file);
            return "Results uploaded successfully!";
        } catch (ResourceNotFoundException e) {
            return "Error: " + e.getMessage();
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @GetMapping("/{competitionId}")
    public ResponseEntity<List<ResultatDto>> getResultsByCompetitionId(@PathVariable Long competitionId) {
        List<ResultatDto> resultats = resultatService.getResultsByCompetitionId(competitionId);
        if (resultats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(resultats, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PatchMapping("/cloture/{competitionId}")
    public ResponseEntity<String> cloturerCompetition(@PathVariable Long competitionId) {
        boolean success = calculService.cloturerCompetitionEtCalculer(competitionId);
        if (success) {
            return ResponseEntity.ok("Compétition clôturée avec succès, calculs appliqués.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur lors de la clôture de la compétition.");
        }
    }


}