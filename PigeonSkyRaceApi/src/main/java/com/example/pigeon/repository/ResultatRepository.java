package com.example.pigeon.repository;

import com.example.pigeon.entity.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
List<Resultat> findByCompetitionId(Long competitionId);
//    Optional<Resultat> findByCompetitionIdAndPigeonId(String competitionId, String pigeonId);
}
