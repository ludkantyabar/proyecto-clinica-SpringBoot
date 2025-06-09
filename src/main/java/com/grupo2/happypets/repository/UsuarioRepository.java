package com.grupo2.happypets.repository;

import com.grupo2.happypets.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}