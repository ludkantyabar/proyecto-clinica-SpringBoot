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
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(Long id, Paciente pacienteActualizado) {
        if (pacienteRepository.existsByDniAndIdPacienteNot(pacienteActualizado.getDni(), id)) {
            throw new IllegalArgumentException("El DNI ya está registrado por otro paciente");
        }
        Paciente paciente = obtenerPacientePorId(id);
        paciente.setDni(pacienteActualizado.getDni());
        paciente.setNombre(pacienteActualizado.getNombre());
        paciente.setApellido(pacienteActualizado.getApellido());
        paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
        paciente.setTelefono(pacienteActualizado.getTelefono());
        paciente.setEmail(pacienteActualizado.getEmail());
        paciente.setDireccion(pacienteActualizado.getDireccion());
        // Actualiza otros campos según sea necesario
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