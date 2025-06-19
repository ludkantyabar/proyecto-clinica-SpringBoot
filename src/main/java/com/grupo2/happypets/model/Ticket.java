package com.grupo2.happypets.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @NotNull(message = "La cita es obligatoria")
    @OneToOne
    @JoinColumn(name = "id_cita", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cita cita;

    @NotBlank(message = "El código del ticket es obligatorio")
    @Size(max = 20, message = "El código del ticket no debe superar los 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String codigoTicket;

    @PastOrPresent(message = "La fecha de impresión debe ser pasada o actual")
    private Date fechaImpresion = new Date();

    @NotBlank(message = "El contenido es obligatorio")
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
}