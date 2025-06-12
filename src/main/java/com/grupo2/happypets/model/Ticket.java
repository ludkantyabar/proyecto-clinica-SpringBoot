package com.grupo2.happypets.model;

import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "id_cita", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cita cita;

    @Column(nullable = false, unique = true, length = 20)
    private String codigoTicket;

    private Date fechaImpresion = new Date();

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
}