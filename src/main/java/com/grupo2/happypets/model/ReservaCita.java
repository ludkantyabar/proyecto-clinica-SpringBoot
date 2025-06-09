package com.grupo2.happypets.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva_cita")
public class ReservaCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaCitaId;

    private String nombre;
    private String email;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    private LocalDate fecha;
    private LocalTime hora;

    public ReservaCita() {}

    public ReservaCita(Long reservaCitaId, String nombre, String email, String telefono, Servicio servicio, LocalDate fecha, LocalTime hora) {
        this.reservaCitaId = reservaCitaId;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Long getReservaCitaId() {
        return reservaCitaId;
    }

    public void setReservaCitaId(Long reservaCitaId) {
        this.reservaCitaId = reservaCitaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}