package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByUsuarioDni(String dniUsuario);

    List<Cita> findByMedicoDni(String dniMedico);

    long countByFechaHoraBetween(LocalDateTime start, LocalDateTime end);

    List<Cita> findByFechaHoraBetweenOrderByFechaHoraAsc(LocalDateTime start, LocalDateTime end);

    @Query("SELECT c FROM Cita c WHERE " +
            "LOWER(c.usuario.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.usuario.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.especialidad.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Cita> buscarCitas(@Param("searchTerm") String searchTerm);
}