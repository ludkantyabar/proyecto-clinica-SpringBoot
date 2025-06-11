package com.grupo2.happypets.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "consultorios")
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultorio;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String ubicacion;

    private String descripcion;

    @OneToMany(mappedBy = "consultorio", cascade = CascadeType.ALL)
    private List<Cita> citas;
}
