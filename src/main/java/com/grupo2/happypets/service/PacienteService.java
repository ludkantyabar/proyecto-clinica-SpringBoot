package com.grupo2.happypets.service;


import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
    }

    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public boolean existePacienteConDni(String dni, Long idPaciente) {
        if (idPaciente == null) {
            return pacienteRepository.existsByDni(dni);
        } else {
            return pacienteRepository.existsByDniAndIdPacienteNot(dni, idPaciente);
        }
    }

    public List<Paciente> buscarPacientes(String searchTerm) {
        return pacienteRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDniContaining(
                searchTerm, searchTerm, searchTerm);
    }

    public Paciente obtenerPacientePorDni(String dni) {
        return pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
    }

    public long countPacientes() {
        return pacienteRepository.count();
    }
}