package com.example.pigeon.service.impl;

import com.example.pigeon.dto.ResultatDto;
import com.example.pigeon.entity.Competition;
import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.entity.Resultat;
import com.example.pigeon.exception.ResourceNotFoundException;
import com.example.pigeon.repository.CompetitionRepository;
import com.example.pigeon.repository.ResultatRepository;
import com.example.pigeon.service.PigeonService;
import com.example.pigeon.service.ResultatService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResultatServiceImpl implements ResultatService {
    @Autowired
    private ResultatRepository resultatRepository;


    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private PigeonService pigeonService;




    @Override
    public List<ResultatDto> getResultsByCompetitionId(Long competitionId) {
        List<Resultat> resultats = resultatRepository.findByCompetitionId(competitionId);
        List<ResultatDto> resultatDtos = new ArrayList<>();
        for (Resultat resultat : resultats) {
            resultatDtos.add(ResultatDto.toDto(resultat));
        }
        return resultatDtos;
    }

    @Override
    public void uploadResultsFile(Long competitionId, MultipartFile file) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Competition not found"));

//        if (!competition.getEstTermine()) {
//            throw new IllegalStateException("The competition must be completed before uploading results.");
//        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<ResultatDto> results = new ArrayList<>();

            // loop to Get numeroBague and heureArrivee from the file also file needs to be excel!!!!
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String numeroBague = row.getCell(0).getStringCellValue();
                Date heureArriveeDate = row.getCell(1).getDateCellValue();

                // Convert Date to LocalDateTime
                LocalDateTime heureArrivee = Instant.ofEpochMilli(heureArriveeDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                // Find pigeon by numeroBague   and get the id
                Pigeon pigeon = pigeonService.findPigeonByNumeroBague(numeroBague)
                        .orElseThrow(() -> new ResourceNotFoundException("Pigeon with numeroBague " + numeroBague + " not found"));

                ResultatDto resultatDto = ResultatDto.builder()
                        .pigeonId(pigeon.getId())
                        .competitionId(competitionId)
                        .heureArrivee(heureArrivee)
                        .distanceParcourue(0)
                        .vitesse(0)
                        .point(0)
                        .build();

                results.add(resultatDto);
            }

            List<Resultat> resultatEntities = results.stream()
                    .map(ResultatDto::toEntity)
                    .toList();
            resultatRepository.saveAll(resultatEntities);

        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

}
