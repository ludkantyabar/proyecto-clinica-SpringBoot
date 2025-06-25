package com.grupo2.happypets.seguridad;

import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PacienteRepository pacienteRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Paciente paciente = pacienteRepositorio.findByDni(username)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado"));

        return User.builder()
                .username(paciente.getDni())
                .password(paciente.getPassword())
                .roles(
                        paciente.getRoles()
                                .stream()
                                .map(rol -> rol.getNombre().replaceFirst("^ROLE_", "")) // Quita el prefijo ROLE_
                                .toArray(String[]::new)
                )
                .build();
    }
}