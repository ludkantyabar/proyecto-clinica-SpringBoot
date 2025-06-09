package com.grupo2.happypets.model;

import jakarta.persistence.*;

@Entity
@Table(name = "especialidad")
public class Especialidad {
    @Id
    private String nombre;

    private String descripcion;

    public Especialidad() {}

    public Especialidad(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}