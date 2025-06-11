package com.grupo2.happypets.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_consultorio")
    private Consultorio consultorio;

    @Column(nullable = false)
    private Date fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    private String motivo;

    @Column(updatable = false)
    private Date fechaCreacion = new Date();

    private String notas;

    public enum EstadoCita {
        PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA
    }
}