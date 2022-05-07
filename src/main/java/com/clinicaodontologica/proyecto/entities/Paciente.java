package com.clinicaodontologica.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "pacientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String apellido;
    @Column
    private String nombre;
    @Column
    private String dni;
    @Column
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;
    @Column
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private Set<Turno> turnos = new HashSet<>();


    public Paciente(){}

    public Paciente(String apellido, String nombre, String dni, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
    }

    public Paciente(Long id, LocalDate fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }
}
