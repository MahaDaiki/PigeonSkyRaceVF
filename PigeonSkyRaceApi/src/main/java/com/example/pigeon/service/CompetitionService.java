package com.example.pigeon.service;

import com.example.pigeon.dto.CompetitionDto;
import com.example.pigeon.entity.Competition;

public interface CompetitionService {
    CompetitionDto addCompetition(CompetitionDto competitionDto);
    CompetitionDto getCompetitionById(Long id);
    CompetitionDto modifyStatus(Long id, Boolean estTermine);

}
