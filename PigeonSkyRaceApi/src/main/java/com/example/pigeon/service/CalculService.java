package com.example.pigeon.service;

import java.time.LocalDateTime;

public interface CalculService {
//    boolean modifyResultAndRecalculate(String competitionId, String pigeonId, LocalDateTime nouvelleHeureArrivee);

    boolean cloturerCompetitionEtCalculer(Long competitionId);
}
