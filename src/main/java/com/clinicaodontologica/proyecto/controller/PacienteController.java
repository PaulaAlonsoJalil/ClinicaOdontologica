package com.clinicaodontologica.proyecto.controller;

import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.service.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);


    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente){
        ResponseEntity<Paciente> respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try{
            respuesta = ResponseEntity.ok(pacienteService.guardar(paciente));
        } catch (Exception e) {
            logger.error("No se pudo guardar el paciente");
            e.printStackTrace();
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscar(id).get());

    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar(){
        ResponseEntity<List<Paciente>> respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        try{
            respuesta = ResponseEntity.ok(pacienteService.listar());
        } catch (Exception e) {
            logger.error("No se pudieron listar los pacientes.");
            e.printStackTrace();
        }

        return respuesta;
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) throws BadRequestException, ResourceNotFoundException {
           return ResponseEntity.ok(pacienteService.actualizar(paciente));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
            pacienteService.eliminar(id);
            return ResponseEntity.ok("Se elimino al paciente con id= " + id);

    }
    
}
