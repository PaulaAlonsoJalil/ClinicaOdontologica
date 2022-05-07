package com.clinicaodontologica.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name="odontologos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String matricula;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @JsonIgnore
    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    private Set<Turno> turnos = new HashSet<>();

    public Odontologo() {
    }

    public Odontologo(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo(Long id, String matricula) {
        this.id = id;
        this.matricula = matricula;
    }
}