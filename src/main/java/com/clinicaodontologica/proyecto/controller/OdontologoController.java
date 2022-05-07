package com.clinicaodontologica.proyecto.controller;

import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.service.OdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    private static final Logger logger = LoggerFactory.getLogger(OdontologoController.class);

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        ResponseEntity<Odontologo> respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            respuesta = ResponseEntity.ok(odontologoService.guardar(odontologo));
        } catch (Exception e) {
            logger.error("No se pudo guardar el odontólogo");
            e.printStackTrace();
        }
        return respuesta;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscar(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listar(){
        ResponseEntity<List<Odontologo>> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        try{
            respuesta = ResponseEntity.ok(odontologoService.listar());
        } catch (Exception e) {
            logger.error("No se pudieron listar los odontólogos");
            e.printStackTrace();
        }
        return respuesta;
    }



    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.actualizar(odontologo));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
            odontologoService.eliminar(id);
            return ResponseEntity.ok("Se elimino al odontologo con id= " + id);

    }
}
