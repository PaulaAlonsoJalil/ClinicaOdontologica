package com.clinicaodontologica.proyecto.service;


import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.entities.Turno;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.repository.TurnoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService{

    @Autowired
    TurnoRepository turnoRepository;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;

    private static final Logger logger = LoggerFactory.getLogger(TurnoService.class);


    public Turno guardar(Turno turno) throws BadRequestException, ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscar(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscar(turno.getOdontologo().getId());
        if (pacienteBuscado.isPresent()&&odontologoBuscado.isPresent()){
            logger.info("Se guardó el turno exitosamente");
            return turnoRepository.save(turno);
        } else if (pacienteBuscado.isEmpty()){
            logger.error("No se encontró el paciente con id " + turno.getPaciente().getId());
            throw new ResourceNotFoundException("No existe un paciente con id= " +  turno.getPaciente().getId());
        } else {
            logger.error("No se encontró el odontólogo con id= " + turno.getOdontologo().getId());
            throw new ResourceNotFoundException("No existe un odontologo con id= " + turno.getOdontologo().getId());
        }
    }

    public List<Turno> listar() {
        logger.info("Se listaron los turnos exitosamente");
        return turnoRepository.findAll();
    }


    public Optional<Turno> buscar(Long id) throws ResourceNotFoundException {

        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            logger.info("Se retornó el turno exitosamente");
            return turnoRepository.findById(id);}
        else {
            logger.error("No se encontró el turno con id " + id);
            throw new ResourceNotFoundException("No existe un turno con id= " + id);
        }
    }


    public Turno actualizar(Turno turno) throws ResourceNotFoundException, BadRequestException {
        if (turno.getId()!=null) {
            Optional<Turno> turnoBuscado = turnoRepository.findById(turno.getId());
            if (turnoBuscado.isPresent()) {
                logger.info("Se actualizó el turno exitosamente");
                return turnoRepository.save(turno);
            } else {
                logger.error("No se encontró el turno con id " + turno.getId());
                throw new ResourceNotFoundException("No existe un turno con id= " + turno.getId());
            }
        } else {
            logger.error("El id no se indicó para actualizar el turno");
            throw new BadRequestException("Indicar el id es obligatorio para actualizar el turno");
        }
    }


    public void eliminar(Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = buscar(id);
        if (turnoBuscado.isPresent()) {
            turnoRepository.deleteById(id);
            logger.info("Se eliminó el turno exitosamente con id= "+ id);
        } else {
            logger.error("No se encontró el turno con id " + id);
            throw new ResourceNotFoundException("No existe el turno con el id= " + id);

        }


    }
}
