package com.clinicaodontologica.proyecto.service;

import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService{
    @Autowired
    PacienteRepository pacienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    public Paciente guardar(Paciente paciente) {
        logger.info("Se guardó el paciente exitosamente");
        return pacienteRepository.save(paciente);
    }
    public List<Paciente> listar() {
        logger.info("Se listaron los pacientes exitosamente");
        return pacienteRepository.findAll();
    }


    public Optional<Paciente> buscar(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()){
            logger.info("Se retornó el paciente exitosamente");
            return pacienteRepository.findById(id);
        } else {
            logger.error("No se encontró el paciente con id " + id);
            throw new ResourceNotFoundException("No existe un paciente con id= " + id);
        }
    }

    //public Optional<Paciente> buscarXEmail(String email) {
      //  return pacienteRepository.findBy(email);
    //}


    public Paciente actualizar(Paciente paciente) throws ResourceNotFoundException, BadRequestException {
        if (paciente.getId()!=null) {
            Optional<Paciente> pacienteBuscado = pacienteRepository.findById(paciente.getId());
            if (pacienteBuscado.isPresent()) {
                logger.info("Se actualizó el paciente exitosamente");
                return pacienteRepository.save(paciente);
            } else {
                logger.error("No se encontró el paciente con id " + paciente.getId());
                throw new ResourceNotFoundException("No existe un paciente con id= " + paciente.getId());
            }
        } else {
            logger.error("El id no se indicó para actualizar el paciente");
            throw new BadRequestException("Indicar el id es obligatorio para actualizar");
        }
    }

    public void eliminar(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()) {
            pacienteRepository.deleteById(id);
            logger.info("Se eliminó el paciente exitosamente con id= "+ id);
        } else {
            logger.error("No se encontró el paciente con id " + id);
            throw new ResourceNotFoundException("No existe un paciente con id= " + id);
        }
    }
}
