//package com.example.pigeon.service.impl;
//
//import com.example.pigeon.dto.UtilisateurDto;
//import com.example.pigeon.entity.Role;
//import com.example.pigeon.entity.Utilisateur;
//import com.example.pigeon.exception.ResourceNotFoundException;
//import com.example.pigeon.repository.UtilisateurRepository;
//import com.example.pigeon.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    @Mock
//    private UtilisateurRepository utilisateurRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private UtilisateurDto utilisateurDto;
//    private Utilisateur utilisateur;
//
//    @BeforeEach
//    void setUp() {
//        utilisateurDto = UtilisateurDto.builder()
//                .username("testUser")
//                .motDePasse("password123")
//                .nom("John Doe")
//                .latitude(34.0522)
//                .longitude(-118.2437)
//                .role(Role.eleveur)
//                .build();
//
//        utilisateur = Utilisateur.builder()
//                .id("1")
//                .username("testUser")
//                .motDePasse("password123")
//                .nom("John Doe")
//                .latitude(34.0522)
//                .longitude(-118.2437)
//                .role(Role.eleveur)
//                .build();
//    }
//
//    @Test
//    void registerUtilisateur_successfulRegistration() {
//
//        when(utilisateurRepository.findByUsername(utilisateurDto.getUsername())).thenReturn(Optional.empty());
//        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);
//
//
//        UtilisateurDto result = userService.registerUtilisateur(utilisateurDto);
//
//
//        assertNotNull(result);
//        assertEquals("testUser", result.getUsername());
//        assertEquals("John Doe", result.getNom());
//        verify(utilisateurRepository, times(1)).findByUsername("testUser");
//        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
//    }
//
//    @Test
//    void registerUtilisateur_duplicateUsername_throwsException() {
//
//        when(utilisateurRepository.findByUsername(utilisateurDto.getUsername())).thenReturn(Optional.of(utilisateur));
//
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.registerUtilisateur(utilisateurDto));
//        assertEquals("Un éleveur avec ce nom de colombier existe déjà", exception.getMessage());
//
//
//        verify(utilisateurRepository, times(1)).findByUsername("testUser");
//        verify(utilisateurRepository, never()).save(any(Utilisateur.class));
//    }
//
//    @Test
//    void findByUsernameAndMotDePasse_validCredentials_returnsUser() {
//
//        when(utilisateurRepository.findByUsernameAndMotDePasse("testUser", "password123")).thenReturn(utilisateur);
//
//        Utilisateur result = userService.findByUsernameAndMotDePasse("testUser", "password123");
//
//        assertNotNull(result);
//        assertEquals("testUser", result.getUsername());
//        assertEquals("password123", result.getMotDePasse());
//        verify(utilisateurRepository, times(1)).findByUsernameAndMotDePasse("testUser", "password123");
//    }
//
//    @Test
//    void findByUsernameAndMotDePasse_invalidCredentials_returnsNull() {
//
//        when(utilisateurRepository.findByUsernameAndMotDePasse("testUser", "wrongPassword")).thenReturn(null);
//
//
//        Utilisateur result = userService.findByUsernameAndMotDePasse("testUser", "wrongPassword");
//
//
//        assertNull(result);
//        verify(utilisateurRepository, times(1)).findByUsernameAndMotDePasse("testUser", "wrongPassword");
//    }
//
//    @Test
//    void getAllUtilisateurs_returnsListOfUsers() {
//
//        List<Utilisateur> utilisateurs = new ArrayList<>();
//        utilisateurs.add(utilisateur);
//
//        when(utilisateurRepository.findAll()).thenReturn(utilisateurs);
//
//
//        List<Utilisateur> result = userService.getAllUtilisateurs();
//
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("testUser", result.get(0).getUsername());
//        verify(utilisateurRepository, times(1)).findAll();
//    }
//
//}