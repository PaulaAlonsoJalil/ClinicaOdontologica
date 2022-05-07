package com.clinicaodontologica.proyecto.repository;


import com.clinicaodontologica.proyecto.entities.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

}
