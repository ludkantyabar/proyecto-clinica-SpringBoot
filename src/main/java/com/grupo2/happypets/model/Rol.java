package com.grupo2.happypets.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRol tipoRol;

    @ManyToMany(mappedBy = "roles")
    private Collection<Paciente> pacientes;

    // Constructor que acepta solo el nombre
    public Rol(String nombre) {
        this.nombre = nombre;
        this.tipoRol = TipoRol.valueOf(nombre.replace("ROLE_", "")); // Ajusta según tu lógica
    }

    // Constructor vacío requerido por JPA
    public Rol() {}
}