package com.grupo2.happypets.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "especialidades")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    @Column(nullable = false, length = 50)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL)
    private List<Medico> medicos;
}