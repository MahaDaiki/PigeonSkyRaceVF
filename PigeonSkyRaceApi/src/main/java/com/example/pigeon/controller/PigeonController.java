package com.example.pigeon.controller;

import com.example.pigeon.dto.PigeonDto;
import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.entity.Role;
import com.example.pigeon.entity.Utilisateur;
import com.example.pigeon.security.CustomUserDetails;
import com.example.pigeon.service.PigeonService;
import com.example.pigeon.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pigeons")
public class PigeonController {

    @Autowired
    private PigeonService pigeonService;
    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<String> addPigeon(@RequestBody PigeonDto pigeonDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUtilisateurId();
        Pigeon pigeon = pigeonDto.toEntity();
        Utilisateur eleveur = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        pigeon.setEleveur(eleveur);
        pigeonService.addPigeon(PigeonDto.toDto(pigeon));
        return ResponseEntity.ok("Pigeon ajouté avec succès");
    }


    @GetMapping
    public ResponseEntity<List<Pigeon>> getAllPigeons() {
        List<Pigeon> pigeons = pigeonService.getAllPigeons();
        return ResponseEntity.ok(pigeons);
    }


//    @GetMapping("/user")
//    public ResponseEntity<List<Pigeon>> getPigeonsByUserId(HttpSession session) {
//        Long userId = (Long) session.getAttribute("utilisateurId");
//
//        if (userId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        List<Pigeon> pigeons = pigeonService.getPigeonsByUserId(userId);
//        return ResponseEntity.ok(pigeons);
//    }

}
