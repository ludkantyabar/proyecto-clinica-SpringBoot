package com.grupo2.happypets.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pagoId;

    @ManyToOne
    @JoinColumn(name = "reserva_cita_id")
    private ReservaCita reservaCita;

    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String metodoPago;

    public Pago() {}

    public Pago(Long pagoId, ReservaCita reservaCita, BigDecimal monto, LocalDateTime fechaPago, String metodoPago) {
        this.pagoId = pagoId;
        this.reservaCita = reservaCita;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public ReservaCita getReservaCita() {
        return reservaCita;
    }

    public void setReservaCita(ReservaCita reservaCita) {
        this.reservaCita = reservaCita;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}