package com.grupo2.happypets.seguridad;

import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class PacienteUserDetailsService implements UserDetailsService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Paciente paciente = pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado"));
        return new PacienteUserDetails(paciente);
    }
}
