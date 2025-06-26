package com.grupo2.happypets.model;

import com.grupo2.happypets.model.Cita;
import com.grupo2.happypets.model.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import com.grupo2.happypets.validation.UniqueDni;

@Data
@Entity
@Table(name = "pacientes", uniqueConstraints = @UniqueConstraint(columnNames = "dni"))
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 12, message = "El DNI debe tener entre 8 y 12 caracteres")
    @Column(nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "paciente_roles",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )

    private Collection<Rol> roles;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Cita> citas;

}