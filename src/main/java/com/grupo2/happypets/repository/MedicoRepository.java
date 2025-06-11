package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidadIdEspecialidad(Long idEspecialidad);
    Optional<Medico> findByDni(String dni);
    boolean existsByDni(String dni);
    long count();
}