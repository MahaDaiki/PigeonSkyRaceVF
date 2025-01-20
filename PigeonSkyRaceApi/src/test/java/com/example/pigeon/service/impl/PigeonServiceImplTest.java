//package com.example.pigeon.service.impl;
//
//import com.example.pigeon.dto.PigeonDto;
//import com.example.pigeon.entity.Pigeon;
//import com.example.pigeon.entity.Utilisateur;
//import com.example.pigeon.repository.PigeonRepository;
//import com.example.pigeon.repository.UtilisateurRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//
//@ExtendWith(MockitoExtension.class)
//class PigeonServiceImplTest {
//
//    @Mock
//    private PigeonRepository pigeonRepository;
//
//    @Mock
//    private UtilisateurRepository utilisateurRepository;
//
//    @InjectMocks
//    private PigeonServiceImpl pigeonService;
//
//    private Utilisateur utilisateur1;
//    private Utilisateur utilisateur2;
//    private Pigeon pigeon1;
//    private Pigeon pigeon2;
//
//
//
//    @BeforeEach
//    void setUp() {
//        utilisateur1 = new Utilisateur();
//        utilisateur1.setId("user1");
//        utilisateur1.setUsername("user1");
//        utilisateur1.setMotDePasse("password1");
//        utilisateur1.setNom("User One");
//        utilisateur1.setLatitude(33.5);
//        utilisateur1.setLongitude(-7.5);
//
//        utilisateur2 = new Utilisateur();
//        utilisateur2.setId("user2");
//        utilisateur2.setUsername("user2");
//        utilisateur2.setMotDePasse("password2");
//        utilisateur2.setNom("User Two");
//        utilisateur2.setLatitude(33.6);
//        utilisateur2.setLongitude(-7.6);
//
//
//        pigeon1 = new Pigeon();
//        pigeon1.setId("pigeon1");
//        pigeon1.setNumeroBague("m123");
//        pigeon1.setCouleur("red");
//        pigeon1.setAge(2);
//        pigeon1.setEleveurId(utilisateur1.getId());
//
//        pigeon2 = new Pigeon();
//        pigeon2.setId("pigeon2");
//        pigeon2.setNumeroBague("f456");
//        pigeon2.setCouleur("blue");
//        pigeon2.setAge(3);
//        pigeon2.setEleveurId(utilisateur2.getId());
//
//
//        utilisateur1.setPigeons(Arrays.asList(pigeon1));
//        utilisateur2.setPigeons(Arrays.asList(pigeon2));
//
//    }
//
//
//    @Test
//    void addPigeon_ShouldReturnPigeonDto_WhenValidData() {
//        String pigeonId = "pigeon1";
//        String eleveurId = "user1";
//        PigeonDto pigeonDto = new PigeonDto("m123", "red", 2, eleveurId);
//
//        Utilisateur utilisateur1 = new Utilisateur();
//        utilisateur1.setId(eleveurId);
//        utilisateur1.setUsername("user1");
//        utilisateur1.setPigeons(new ArrayList<>());
//
//        Pigeon pigeon1 = new Pigeon("m123", "red", 2, eleveurId);
//        pigeon1.setId(pigeonId);
//
//
//        when(pigeonRepository.save(any(Pigeon.class))).thenReturn(pigeon1);
//        when(utilisateurRepository.findById(eleveurId)).thenReturn(Optional.of(utilisateur1));
//
//        PigeonDto result = pigeonService.addPigeon(pigeonDto);
//
//
//        assertNotNull(result);
//        assertEquals(pigeonDto.getNumeroBague(), result.getNumeroBague());
//        assertEquals(pigeonDto.getCouleur(), result.getCouleur());
//        assertEquals(pigeonDto.getAge(), result.getAge());
//        assertEquals(pigeonDto.getEleveurId(), result.getEleveurId());
//
//        verify(pigeonRepository, times(1)).save(any(Pigeon.class));
//        verify(utilisateurRepository, times(1)).findById(eleveurId);
//    }
//    @Test
//    void addPigeon_ShouldThrowException_WhenAgeIsInvalid() {
//
//        PigeonDto pigeonDto = new PigeonDto("M123", "red", -1, "eleveurId1");
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            pigeonService.addPigeon(pigeonDto);
//        });
//        assertEquals("L'âge doit être supérieur à 0", exception.getMessage());
//    }
//
//    @Test
//    void getAllPigeons_ShouldReturnListOfPigeons() {
//        Pigeon pigeon1 = new Pigeon("m123", "red", 2, "eleveurId1");
//        Pigeon pigeon2 = new Pigeon("f456", "blue", 3, "eleveurId2");
//        List<Pigeon> pigeons = Arrays.asList(pigeon1, pigeon2);
//        when(pigeonRepository.findAll()).thenReturn(pigeons);
//
//        List<Pigeon> result = pigeonService.getAllPigeons();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("m123", result.get(0).getNumeroBague());
//        assertEquals("f456", result.get(1).getNumeroBague());
//        verify(pigeonRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getPigeonsByUserId_ShouldReturnPigeons_WhenUserIdExists() {
//        String eleveurId = "eleveurId1";
//        Pigeon pigeon1 = new Pigeon("m123", "red", 2, eleveurId);
//        Pigeon pigeon2 = new Pigeon("f456", "blue", 3, eleveurId);
//        List<Pigeon> pigeons = Arrays.asList(pigeon1, pigeon2);
//        when(pigeonRepository.findByEleveurId(eleveurId)).thenReturn(pigeons);
//
//        List<Pigeon> result = pigeonService.getPigeonsByUserId(eleveurId);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(eleveurId, result.get(0).getEleveurId());
//        verify(pigeonRepository, times(1)).findByEleveurId(eleveurId);
//    }
//
//    @Test
//    void getPigeonsByUserId_ShouldReturnEmptyList_WhenUserIdDoesNotExist() {
//        String eleveurId = "nonExistentId";
//        when(pigeonRepository.findByEleveurId(eleveurId)).thenReturn(Arrays.asList());
//
//        List<Pigeon> result = pigeonService.getPigeonsByUserId(eleveurId);
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//        verify(pigeonRepository, times(1)).findByEleveurId(eleveurId);
//    }
//
//    @Test
//    void getPigeonsByIds_ShouldReturnPigeons_WhenIdsExist() {
//
//        List<String> pigeonIds = Arrays.asList("m123", "f456");
//        Pigeon pigeon1 = new Pigeon("m123", "red", 2, "eleveurId1");
//        Pigeon pigeon2 = new Pigeon("f456", "blue", 3, "eleveurId2");
//        when(pigeonRepository.findAllById(pigeonIds)).thenReturn(Arrays.asList(pigeon1, pigeon2));
//
//        List<Pigeon> result = pigeonService.getPigeonsByIds(pigeonIds);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("m123", result.get(0).getNumeroBague());
//        assertEquals("f456", result.get(1).getNumeroBague());
//        verify(pigeonRepository, times(1)).findAllById(pigeonIds);
//    }
//
//    @Test
//    void getPigeonsByIds_ShouldReturnEmptyList_WhenIdsDoNotExist() {
//
//        List<String> pigeonIds = Arrays.asList("nonExistentId1", "nonExistentId2");
//        when(pigeonRepository.findAllById(pigeonIds)).thenReturn(Arrays.asList());
//
//        List<Pigeon> result = pigeonService.getPigeonsByIds(pigeonIds);
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//        verify(pigeonRepository, times(1)).findAllById(pigeonIds);
//    }
//
//}