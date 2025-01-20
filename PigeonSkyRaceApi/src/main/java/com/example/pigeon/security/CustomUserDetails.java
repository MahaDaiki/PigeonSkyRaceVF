package com.example.pigeon.security;
import com.example.pigeon.entity.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long utilisateurId;

    public CustomUserDetails(Utilisateur utilisateur, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(utilisateur.getUsername(), utilisateur.getMotDePasse(), authorities);
        this.utilisateurId = utilisateur.getId();

    }
}
