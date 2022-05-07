package com.clinicaodontologica.proyecto.service;
import com.clinicaodontologica.proyecto.entities.Domicilio;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;



    public void cargarDatos(){
        Domicilio domicilio = new Domicilio(
                "Imaginaria",
                "27",
                "Irlanda",
                "Argentina"
        );
        Paciente paciente = pacienteService.guardar(
                new Paciente(
                        "Alonso",
                        "Paula",
                        "23432435",
                        LocalDate.of(2022,4,1),
                        domicilio,
                        "fakeemail@gmail.com"
                )
        );
    }

    @Test
    public void b_buscarPacienteTest() throws ResourceNotFoundException {
        Assert.assertNotNull(pacienteService.buscar(1L));
    }

    @Test
    public void a_registroPacienteTest() throws BadRequestException, ResourceNotFoundException {
        this.cargarDatos();
        Paciente paciente = pacienteService.guardar(
                pacienteService.buscar(1L).get());
        Assert.assertNotNull(paciente);
    }

    //EL ASSERT DEPENDE DE LO QUE QUIERA MODIFICAR; EN ESTE CASO LA FECHA
    @Test
    public void c_actualizarPacienteTest() throws ResourceNotFoundException, BadRequestException {
        Paciente paciente = pacienteService.actualizar(
                new Paciente(1L, LocalDate.of(2022,10,1))
                );
        Assert.assertEquals(
                LocalDate.of(2022,10,1),paciente.getFechaIngreso()
        );
    }

    @Test
    public void d_listarPacienteTest() {
        Assert.assertNotNull(pacienteService.listar());
    }

    @Test
    public void e_eliminarTurnoTest() throws ResourceNotFoundException {
        pacienteService.eliminar(1L);
        Assert.assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscar(1L));
    }
}
