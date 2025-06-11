package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultorioRepository extends JpaRepository<Consultorio, Long> {
    Optional<Consultorio> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    long count();
}