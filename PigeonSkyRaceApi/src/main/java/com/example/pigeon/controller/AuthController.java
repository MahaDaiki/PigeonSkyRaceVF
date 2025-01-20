package com.example.pigeon.controller;

import com.example.pigeon.dto.LoginRequestDto;
import com.example.pigeon.dto.UtilisateurDto;
import com.example.pigeon.entity.Utilisateur;
import com.example.pigeon.exception.EmptyException;
import com.example.pigeon.service.UserService;
import com.example.pigeon.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.bouncycastle.cms.RecipientId.password;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UtilisateurDto> registerUtilisateur(@RequestBody @Valid UtilisateurDto user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new EmptyException("Username cannot be null or empty.");
        }
        if (user.getMotDePasse() == null || user.getMotDePasse().trim().isEmpty()) {
            throw new EmptyException("Password cannot be null or empty.");
        }

        UtilisateurDto registeredUtilisateur = userService.registerUtilisateur(user);
        return new ResponseEntity<>(registeredUtilisateur, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getMotDePasse())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            System.out.println("Authentication failed for username: " + loginRequest.getUsername());
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("User logged out successfully");
    }

}





