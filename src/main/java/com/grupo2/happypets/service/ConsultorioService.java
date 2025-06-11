package com.grupo2.happypets.service;

import com.grupo2.happypets.model.Consultorio;
import com.grupo2.happypets.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Consultorio> obtenerTodosConsultorios() {
        return consultorioRepository.findAll();
    }

    public Consultorio guardarConsultorio(Consultorio consultorio) {
        return consultorioRepository.save(consultorio);
    }

    public Consultorio obtenerConsultorioPorId(Long id) {
        return consultorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado con ID: " + id));
    }

    public void eliminarConsultorio(Long id) {
        // Verificar si el consultorio tiene citas asociadas antes de eliminar
        if(!consultorioRepository.existsById(id)) {
            throw new RuntimeException("Consultorio no encontrado con ID: " + id);
        }
        consultorioRepository.deleteById(id);
    }

    public Consultorio obtenerConsultorioPorCodigo(String codigo) {
        return consultorioRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado con código: " + codigo));
    }

    public boolean existeConsultorioPorCodigo(String codigo) {
        return consultorioRepository.existsByCodigo(codigo);
    }

    public long countConsultorios() {
        return consultorioRepository.count();
    }
}