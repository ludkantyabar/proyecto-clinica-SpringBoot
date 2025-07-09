package com.grupo2.happypets.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "El médico es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_consultorio")
    private Consultorio consultorio;

    @NotNull(message = "La fecha y hora es obligatoria")
    @Future(message = "La fecha y hora debe estar en el futuro")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHora;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @Column(updatable = false)
    private Date fechaCreacion = new Date();

    private String notas;

    // Campo transitorio para recibir solo la fecha desde el formulario
    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    public enum EstadoCita {
        PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA
    }
}