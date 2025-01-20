    package com.example.pigeon.service.impl;

    import com.example.pigeon.dto.UtilisateurDto;
    import com.example.pigeon.entity.Role;
    import com.example.pigeon.entity.Utilisateur;
    import com.example.pigeon.exception.ResourceNotFoundException;
    import com.example.pigeon.exception.UsernameAlreadyExistsException;
    import com.example.pigeon.repository.UtilisateurRepository;
    import com.example.pigeon.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class UserServiceImpl implements UserService {

        @Autowired
        private UtilisateurRepository utilisateurRepository;



        public Utilisateur findByUsernameAndMotDePasse(String username, String motDePasse) {
            return utilisateurRepository.findByUsernameAndMotDePasse(username, motDePasse);
        }

        @Override
        public UtilisateurDto registerUtilisateur(UtilisateurDto userDto) {
            if (utilisateurRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username '" + userDto.getUsername() + "' already exists.");
            }
            if (userDto.getRole() == null) {
                userDto.setRole(Role.ROLE_USER);
            }
            Utilisateur utilisateur = userDto.toEntity();
            var bCryptEncoder = new BCryptPasswordEncoder();
            utilisateur.setMotDePasse(bCryptEncoder.encode(utilisateur.getMotDePasse()));
            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            return UtilisateurDto.toDto(savedUtilisateur);
        }


        @Override
        public List<Utilisateur> getAllUtilisateurs() {
            return utilisateurRepository.findAll();
        }

        @Override
        public UtilisateurDto changeUserRole(Long userId, Role newRole) {
            Utilisateur utilisateur = utilisateurRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

            utilisateur.setRole(newRole);
            utilisateurRepository.save(utilisateur);

            return UtilisateurDto.toDto(utilisateur);
        }

        @Override
        public Optional<Utilisateur> findById(Long userId) {
            return utilisateurRepository.findById(userId);
        }


    }
