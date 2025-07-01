package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDni(String dni);
    boolean existsByDni(String dni);
    boolean existsByDniAndIdUsuarioNot(String dni, Long idUsuario);

    @Query("SELECT u FROM Usuario u WHERE " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "u.dni LIKE CONCAT('%', :searchTerm, '%')")
        // CORRIGE O AGREGA ESTA LÍNEA:
    List<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDniContaining(
            String nombre, String apellido, String dni
    );

    long count();
}