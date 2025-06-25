package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Rol;
import com.grupo2.happypets.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombre(String nombre);
    Rol findByNombre(String nombre);
    Rol findByTipoRol(TipoRol tipoRol);
}