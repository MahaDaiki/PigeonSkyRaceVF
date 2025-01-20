package com.example.pigeon.service;

import com.example.pigeon.dto.UtilisateurDto;
import com.example.pigeon.entity.Role;
import com.example.pigeon.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Utilisateur findByUsernameAndMotDePasse(String username, String motDePasse);
    UtilisateurDto registerUtilisateur(UtilisateurDto user);
    List<Utilisateur> getAllUtilisateurs();
    UtilisateurDto changeUserRole(Long userId, Role newRole);
    Optional<Utilisateur> findById(Long userId);
}
