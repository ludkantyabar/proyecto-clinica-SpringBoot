package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Especialidad;
import com.grupo2.happypets.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> obtenerTodasEspecialidades() {
        return especialidadRepository.findAll();
    }

    public Especialidad guardarEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad obtenerEspecialidadPorId(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
    }

    public void eliminarEspecialidad(Long id) {
        // Verificar si la especialidad tiene médicos asociados antes de eliminar
        if(!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }

    public boolean existeEspecialidadPorNombre(String nombre) {
        return especialidadRepository.existsByNombre(nombre);
    }
}