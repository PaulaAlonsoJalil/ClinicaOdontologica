package com.clinicaodontologica.proyecto.service;

import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.repository.OdontologoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService{
    private static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

    @Autowired
    OdontologoRepository odontologoRepository;

    public Odontologo guardar(Odontologo odontologo) {
        logger.info("Se guardó exitosamente el odontologo.");
        return odontologoRepository.save(odontologo);
    }

    public List<Odontologo> listar() {
        logger.info("Se listó exitosamente los odontologos.");
        return odontologoRepository.findAll();
    }

    public Optional<Odontologo> buscar(Long id) throws ResourceNotFoundException {

        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()){
            logger.info("Se retornó el odontólogo exitosamente");
            return odontologoRepository.findById(id);
        } else {
            logger.error("No se encontró el odontólogo con id " + id);
            throw new ResourceNotFoundException("No existe un odontólogo con id= " + id);
        }
    }

    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException, BadRequestException {
        if (odontologo.getId()!=null) {
            Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(odontologo.getId());
            if (odontologoBuscado.isPresent()) {
                logger.info("Se actualizó exitosamente el odontologo.");
                return odontologoRepository.save(odontologo);
            } else {
                logger.error("No se encontró el odontólogo con id " + odontologo.getId());
                throw new ResourceNotFoundException("No existe un odontólogo con id= " + odontologo.getId());
            }
        } else {
            logger.error("El id no se indicó para actualizar el odontólogo");
            throw new BadRequestException("Indicar el id es obligatorio para actualizar");
        }
    }

    public void eliminar(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()){
            odontologoRepository.deleteById(id);
            logger.info("Se borro exitosamente el odontologo.");
        } else {
            logger.error("No se pudo eliminar el odontologo con id = "+ id);
            throw new ResourceNotFoundException("No existe un odontologo con id= " + id);

        }

    }
}
