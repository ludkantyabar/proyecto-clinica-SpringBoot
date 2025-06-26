package com.grupo2.happypets.seguridad;

import com.grupo2.happypets.model.Paciente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public class PacienteUserDetails implements UserDetails {

    private final Paciente paciente;

    public PacienteUserDetails(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return paciente.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getTipoRol().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return paciente.getPassword();
    }

    @Override
    public String getUsername() {
        return paciente.getDni();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}