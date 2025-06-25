package com.grupo2.happypets.servicio;

import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepository pacienteRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Paciente registrarPaciente(Paciente paciente) {
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        paciente.setRoles(Collections.singleton(new Rol("ROLE_PACIENTE")));
        return pacienteRepositorio.save(paciente);
    }
}