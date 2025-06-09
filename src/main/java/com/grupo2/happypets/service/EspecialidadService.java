package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Especialidad;
import com.grupo2.happypets.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> findById(String nombre) {
        return especialidadRepository.findById(nombre);
    }

    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public void deleteById(String nombre) {
        especialidadRepository.deleteById(nombre);
    }
}