package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Paciente;
import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import com.grupo2.happypets.repository.PacienteRepository;
import com.grupo2.happypets.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente guardarPaciente(Paciente paciente) {
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        // Cifrar la contraseña antes de guardar
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
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

    public void registrarPaciente(Paciente paciente) {
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        if (paciente.getRoles() == null || paciente.getRoles().isEmpty()) {
            Rol rolPaciente = rolRepository.findByTipoRol(TipoRol.PACIENTE);
            if (rolPaciente == null) {
                throw new RuntimeException("No existe el rol PACIENTE en la base de datos");
            }
            paciente.setRoles(List.of(rolPaciente));
        }
        // Cifrar la contraseña antes de guardar
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        pacienteRepository.save(paciente);
    }
}