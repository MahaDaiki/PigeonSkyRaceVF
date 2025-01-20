package com.example.pigeon.controller;


import com.example.pigeon.dto.UtilisateurDto;
import com.example.pigeon.entity.Role;
import com.example.pigeon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = userService.getAllUtilisateurs()
                .stream()
                .map(UtilisateurDto::toDto)
                .toList();
        return ResponseEntity.ok(utilisateurs);
    }


    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtilisateurDto> changeUserRole(
            @PathVariable Long userId,
            @RequestBody Role newRole) {
        UtilisateurDto updatedUser = userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }
}
