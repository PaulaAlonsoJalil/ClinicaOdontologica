package com.clinicaodontologica.proyecto.repository;

import com.clinicaodontologica.proyecto.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
