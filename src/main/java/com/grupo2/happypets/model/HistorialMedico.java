package com.grupo2.happypets.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historiales_medicos")
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(nullable = false, length = 1000)
    private String diagnostico;

    @Column(length = 1000)
    private String tratamiento;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
}