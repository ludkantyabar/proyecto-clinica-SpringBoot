package com.grupo2.happypets.service;


import com.grupo2.happypets.model.Medico;
import com.grupo2.happypets.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadService especialidadService;

    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.findAll();
    }

    public Medico guardarMedico(Medico medico) {
        // Validar que la especialidad exista
        if(medico.getEspecialidad() != null && medico.getEspecialidad().getIdEspecialidad() != null) {
            medico.setEspecialidad(
                    especialidadService.obtenerEspecialidadPorId(
                            medico.getEspecialidad().getIdEspecialidad()
                    )
            );
        }
        return medicoRepository.save(medico);
    }

    public Medico obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
    }

    public void eliminarMedico(Long id) {
        // Verificar si el médico tiene citas asociadas antes de eliminar
        if(!medicoRepository.existsById(id)) {
            throw new RuntimeException("Médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    public List<Medico> obtenerMedicosPorEspecialidad(Long idEspecialidad) {
        return medicoRepository.findByEspecialidadIdEspecialidad(idEspecialidad);
    }

    public Medico obtenerMedicoPorDni(String dni) {
        return medicoRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con DNI: " + dni));
    }

    public long countMedicos() {
        return medicoRepository.count();
    }


}