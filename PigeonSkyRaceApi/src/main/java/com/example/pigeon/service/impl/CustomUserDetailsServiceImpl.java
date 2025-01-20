package com.example.pigeon.service.impl;

import com.example.pigeon.entity.Utilisateur;
import com.example.pigeon.repository.UtilisateurRepository;
import com.example.pigeon.security.CustomUserDetails;
import com.example.pigeon.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        System.out.println("getUsername"+utilisateur.getUsername());
        System.out.println( utilisateur.getMotDePasse());
        System.out.println("ROLE: "+utilisateur.getRole().name());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(utilisateur.getRole().name());

        return new CustomUserDetails(utilisateur, Collections.singletonList(authority), utilisateur.getId());
    }
}
