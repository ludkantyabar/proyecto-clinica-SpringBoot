package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    List<HistorialMedico> findByUsuarioIdUsuario(Long idUsuario);
}