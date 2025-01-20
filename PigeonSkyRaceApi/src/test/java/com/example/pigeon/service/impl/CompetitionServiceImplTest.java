//package com.example.pigeon.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.example.pigeon.dto.CompetitionDto;
//import com.example.pigeon.dto.CompetitionRequestDto;
//import com.example.pigeon.dto.PigeonDto;
//import com.example.pigeon.entity.Competition;
//import com.example.pigeon.entity.Pigeon;
//import com.example.pigeon.repository.CompetitionRepository;
//import com.example.pigeon.repository.PigeonRepository;
//import com.example.pigeon.service.PigeonService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
//class CompetitionServiceImplTest {
//    @Mock
//    private CompetitionRepository competitionRepository;
//
//    @Mock
//    private PigeonService pigeonService;
//
//    @InjectMocks
//    private CompetitionServiceImpl competitionService;
//
//    private Pigeon pigeon1;
//    private Pigeon pigeon2;
//    private CompetitionDto competitionDto;
//    private Competition competition;
//
//    @BeforeEach
//    void setUp() {
//        pigeon1 = new Pigeon("pigeon1", "red", 2, "eleveurId1");
//        pigeon2 = new Pigeon("pigeon2", "blue", 3, "eleveurId2");
//
//        competitionDto = new CompetitionDto();
//        competitionDto.setNomCourse("Competition 1");
//        competitionDto.setLatitudeLacher(10.123);
//        competitionDto.setLongitudeLacher(20.456);
//        competitionDto.setDateHeureDepart(LocalDateTime.now().plusDays(1));
//        competitionDto.setDistancePrevisionnelle(500);
//        competitionDto.setSeason("2024");
//        competitionDto.setEstTermine(false);
//        competitionDto.setPigeonIds(Arrays.asList("pigeon1", "pigeon2"));
//
//
//        competition = new Competition(
//                "Competition 1",
//                10.123,
//                20.456,
//                LocalDateTime.now().plusDays(1),
//                500,
//                "2024",
//                false,
//                Arrays.asList(pigeon1, pigeon2)
//        );
//    }
//
//    @Test
//    void addCompetition_validData() {
//        when(pigeonService.getPigeonsByIds(Arrays.asList("pigeon1", "pigeon2"))).thenReturn(Arrays.asList(pigeon1, pigeon2));
//        when(competitionRepository.save(any(Competition.class))).thenReturn(competition);
//
//        competitionDto.setDateHeureDepart(LocalDateTime.now().plusMinutes(10));
//
//        CompetitionDto result = competitionService.addCompetition(competitionDto);
//
//        assertNotNull(result);
//        assertEquals(competitionDto.getNomCourse(), result.getNomCourse());
//        assertEquals(competitionDto.getPigeonIds().size(), result.getPigeonIds().size());
//        verify(competitionRepository, times(1)).save(any(Competition.class));
//    }
//
//
//    @Test
//    void getCompetitionById_found() {
//        when(competitionRepository.findById("1")).thenReturn(Optional.of(competition));
//
//        CompetitionDto result = competitionService.getCompetitionById("1");
//
//        assertNotNull(result);
//        assertEquals(competition.getNomCourse(), result.getNomCourse());
//        verify(competitionRepository, times(1)).findById("1");
//    }
//
//    @Test
//    void getCompetitionById_notFound() {
//        when(competitionRepository.findById("1")).thenReturn(Optional.empty());
//
//        CompetitionDto result = competitionService.getCompetitionById("1");
//
//        assertNull(result);
//        verify(competitionRepository, times(1)).findById("1");
//    }
//
//    @Test
//    void modifyStatus_validData() {
//        when(competitionRepository.findById("1")).thenReturn(Optional.of(competition));
//        when(competitionRepository.save(any(Competition.class))).thenReturn(competition);
//
//        CompetitionDto result = competitionService.modifyStatus("1", true);
//
//        assertNotNull(result);
//        assertTrue(result.getEstTermine());
//        verify(competitionRepository, times(1)).save(any(Competition.class));
//    }
//
//    @Test
//    void modifyStatus_success() {
//        CompetitionDto competitionDto = new CompetitionDto();
//        competitionDto.setEstTermine(true);
//        competitionDto.setNomCourse("Competition 1");
//
//        Competition competition = new Competition();
//        competition.setId("1");
//        competition.setEstTermine(false);
//
//        when(competitionRepository.findById("1")).thenReturn(java.util.Optional.of(competition));
//        when(competitionRepository.save(any(Competition.class))).thenReturn(competition);
//
//        CompetitionDto result = competitionService.modifyStatus("1", true);
//
//        assertNotNull(result);
//        assertTrue(result.getEstTermine());
//        verify(competitionRepository, times(1)).save(any(Competition.class));
//    }
//    @Test
//    void addCompetition_emptyPigeonIds() {
//        competitionDto.setPigeonIds(Arrays.asList());
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            competitionService.addCompetition(competitionDto);
//        });
//        assertEquals("La liste des IDs de pigeon est vide", exception.getMessage());
//        verify(competitionRepository, times(0)).save(any(Competition.class));
//    }
//
//}