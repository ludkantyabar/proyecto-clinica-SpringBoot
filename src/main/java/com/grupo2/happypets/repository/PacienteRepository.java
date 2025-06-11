package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(String dni);
    boolean existsByDni(String dni);
    boolean existsByDniAndIdPacienteNot(String dni, Long idPaciente);

    @Query("SELECT p FROM Paciente p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "p.dni LIKE CONCAT('%', :searchTerm, '%')")
    List<Paciente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDniContaining(
            @Param("searchTerm") String searchTerm1,
            @Param("searchTerm") String searchTerm2,
            @Param("searchTerm") String searchTerm3);
    long count();
}