package com.clinicaodontologica.proyecto.service;

import com.clinicaodontologica.proyecto.entities.Domicilio;
import com.clinicaodontologica.proyecto.repository.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DomicilioService {
    @Autowired
    DomicilioRepository domicilioRepository;

    public Domicilio guardar(Domicilio domicilio) {
        return domicilioRepository.save(domicilio);
    }


    public List<Domicilio> listar() {
        return domicilioRepository.findAll();
    }


    public Optional<Domicilio> buscar(Long id) {
        return domicilioRepository.findById(id);
    }


    public Domicilio actualizar(Domicilio domicilio) {
        if (buscar(domicilio.getId()).isPresent()){
            return domicilioRepository.save(domicilio);
        }
        return null;
    }


    public void eliminar(Long id) {
        domicilioRepository.deleteById(id);
    }

}
