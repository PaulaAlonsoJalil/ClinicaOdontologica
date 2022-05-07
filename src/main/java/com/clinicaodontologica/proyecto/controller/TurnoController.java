package com.clinicaodontologica.proyecto.controller;


import com.clinicaodontologica.proyecto.entities.Turno;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.service.OdontologoService;
import com.clinicaodontologica.proyecto.service.PacienteService;
import com.clinicaodontologica.proyecto.service.TurnoService;
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
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    private static final Logger logger = LoggerFactory.getLogger(TurnoController.class);


    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.guardar(turno));
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){

        ResponseEntity<List<Turno>> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        try{
            respuesta = ResponseEntity.ok(turnoService.listar());
        } catch (Exception e) {
            logger.error("No se pudieron listar los turnos.");
            e.printStackTrace();
        }

        return respuesta;
    }



    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscar(id).get());
    }


    @PutMapping
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.actualizar(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
            turnoService.eliminar(id);
            return ResponseEntity.ok("Se elimino al turno con id= " + id);
    }

}
