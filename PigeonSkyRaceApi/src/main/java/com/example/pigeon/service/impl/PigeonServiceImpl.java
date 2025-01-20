package com.example.pigeon.service.impl;

import com.example.pigeon.dto.PigeonDto;
import com.example.pigeon.entity.Pigeon;
import com.example.pigeon.entity.Utilisateur;
import com.example.pigeon.repository.PigeonRepository;
import com.example.pigeon.repository.UtilisateurRepository;
import com.example.pigeon.service.PigeonService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PigeonServiceImpl implements PigeonService {

    @Autowired
    private PigeonRepository pigeonRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public PigeonDto addPigeon(PigeonDto pigeonDto) {


        Pigeon pigeon = pigeonDto.toEntity();
        if (pigeon.getAge() <= 0) {
            throw new IllegalArgumentException("L'âge doit être supérieur à 0");
        }
        if (!pigeon.getNumeroBague().toLowerCase().startsWith("m") &&
                !pigeon.getNumeroBague().toLowerCase().startsWith("f")) {
            throw new IllegalArgumentException("Le numéro de bague doit commencer par 'm' pour mâle ou 'f' pour femelle");
        }

        Utilisateur eleveur = utilisateurRepository.findById(pigeonDto.getEleveurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));


        pigeon.setEleveur(eleveur);
        Pigeon savedPigeon = pigeonRepository.save(pigeon);

        return PigeonDto.toDto(savedPigeon);
    }

    @Override
    public List<Pigeon> getAllPigeons() {
        return pigeonRepository.findAll();
    }

    @Override
    public List<Pigeon> getPigeonsByUserId(Long eleveurId) {
        return pigeonRepository.findByEleveurId(eleveurId);
    }

    @Override
    public List<Pigeon> getPigeonsByIds(List<Long> pigeonIds) {
        return pigeonRepository.findAllById(pigeonIds);
    }

    @Override
    public Optional<Pigeon> findPigeonByNumeroBague(String numeroBague) {
        return pigeonRepository.findByNumeroBague(numeroBague);
    }


}
