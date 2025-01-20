//package com.example.pigeon.service.impl;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.junit.jupiter.api.Assertions.*;
//import com.example.pigeon.dto.ResultatDto;
//import com.example.pigeon.entity.Competition;
//import com.example.pigeon.entity.Pigeon;
//import com.example.pigeon.entity.Resultat;
//import com.example.pigeon.exception.ResourceNotFoundException;
//import com.example.pigeon.repository.CompetitionRepository;
//import com.example.pigeon.repository.ResultatRepository;
//import com.example.pigeon.service.PigeonService;
//import com.example.pigeon.service.impl.ResultatServiceImpl;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.Mockito;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//
//@ExtendWith(MockitoExtension.class)
//class ResultatServiceImplTest {
//    @Mock
//    private ResultatRepository resultatRepository;
//
//    @Mock
//    private CompetitionRepository competitionRepository;
//
//    @Mock
//    private PigeonService pigeonService;
//
//    @InjectMocks
//    private ResultatServiceImpl resultatService;
//
//    private Competition competition;
//    private Pigeon pigeon;
//    private Resultat resultat;
//    private ResultatDto resultatDto;
//
//    @BeforeEach
//    void setUp() {
//        competition = new Competition();
//        competition.setId("competitionId");
//
//        pigeon = new Pigeon();
//        pigeon.setId("pigeonId");
//        pigeon.setNumeroBague("M123");
//
//        resultat = new Resultat("competitionId", "pigeonId", 0, 0, null, LocalDateTime.now(), 0,0);
//        resultatDto = ResultatDto.builder()
//                .competitionId("competitionId")
//                .pigeonId("pigeonId")
//                .heureArrivee(LocalDateTime.now())
//                .distanceParcourue(0)
//                .vitesse(0)
//                .point(0)
//                .build();
//    }
//
//    @Test
//    void testGetResultsByCompetitionId() {
//        when(resultatRepository.findByCompetitionId("competitionId"))
//                .thenReturn(Collections.singletonList(resultat));
//
//        List<ResultatDto> results = resultatService.getResultsByCompetitionId("competitionId");
//
//        assertEquals(1, results.size());
//        verify(resultatRepository, times(1)).findByCompetitionId("competitionId");
//    }
//    @Test
//    void testUploadResultsFile_PigeonNotFound() throws IOException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        var sheet = workbook.createSheet("Sheet1");
//        var headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("NumeroBague");
//        headerRow.createCell(1).setCellValue("HeureArrivee");
//
//        var dataRow = sheet.createRow(1);
//        dataRow.createCell(0).setCellValue("B124");
//         dataRow.createCell(1).setCellValue(LocalDateTime.now());
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//
//        MultipartFile file = mock(MultipartFile.class);
//        when(file.getInputStream()).thenReturn(inputStream);
//
//        when(competitionRepository.findById("competitionId")).thenReturn(Optional.of(competition));
//        when(pigeonService.findPigeonByNumeroBague("B124")).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> resultatService.uploadResultsFile("competitionId", file));
//
//        verify(competitionRepository, times(1)).findById("competitionId");
//        verify(pigeonService, times(1)).findPigeonByNumeroBague("B124");
//        verifyNoInteractions(resultatRepository);
//    }
//    @Test
//    void testUploadResultsFile() throws IOException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        var sheet = workbook.createSheet("Sheet1");
//        var headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("NumeroBague");
//        headerRow.createCell(1).setCellValue("HeureArrivee");
//
//        var dataRow = sheet.createRow(1);
//        dataRow.createCell(0).setCellValue("M123");
//        dataRow.createCell(1).setCellValue(LocalDateTime.now());
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//
//        MultipartFile file = mock(MultipartFile.class);
//        when(file.getInputStream()).thenReturn(inputStream);
//
//        when(competitionRepository.findById("competitionId")).thenReturn(Optional.of(competition));
//        when(pigeonService.findPigeonByNumeroBague("M123")).thenReturn(Optional.of(pigeon));
//        when(resultatRepository.saveAll(anyList())).thenReturn(Collections.singletonList(resultat));
//
//        assertDoesNotThrow(() -> resultatService.uploadResultsFile("competitionId", file));
//
//
//        verify(competitionRepository, times(1)).findById("competitionId");
//        verify(pigeonService, times(1)).findPigeonByNumeroBague("M123");
//        verify(resultatRepository, times(1)).saveAll(anyList());
//    }
//
//}