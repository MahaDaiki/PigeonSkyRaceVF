package com.example.pigeon.repository;

import com.example.pigeon.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsernameAndMotDePasse(String username, String motDePasse);
    Optional<Utilisateur> findByUsername(String username);

//    @Query("{ 'pigeons.$id': ObjectId(?0) }")
//    Optional<Utilisateur> findByPigeonId(Long pigeonId);

    Optional<Utilisateur> findByPigeons_Id(Long pigeonId);
}
