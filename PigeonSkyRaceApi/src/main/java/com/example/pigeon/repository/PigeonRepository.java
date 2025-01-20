package com.example.pigeon.repository;

import com.example.pigeon.entity.Pigeon;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


@Repository
public interface PigeonRepository extends JpaRepository<Pigeon, Long> {
    List<Pigeon> findByEleveurId(Long eleveurId);
    Optional<Pigeon> findByNumeroBague(String numeroBague);
}
