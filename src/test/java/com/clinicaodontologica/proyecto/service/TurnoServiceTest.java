package com.clinicaodontologica.proyecto.service;
import com.clinicaodontologica.proyecto.entities.Domicilio;
import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.entities.Turno;
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
public class TurnoServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;


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
        odontologoService.guardar(
                new Odontologo(
                        "2343424",
                        "Este",
                        "la")
                );
    }

    @Test
    public void b_buscarTurnoTest() throws ResourceNotFoundException {
        Assert.assertNotNull(turnoService.buscar(1L));
    }

    @Test
    public void a_registroTurnoTest() throws BadRequestException, ResourceNotFoundException {
        this.cargarDatos();
        Turno turno = turnoService.guardar(new Turno(
                pacienteService.buscar(1L).get(),
                odontologoService.buscar(1L).get(),
                LocalDate.of(2022,06,10)));
        Assert.assertNotNull(turno);
    }

    @Test
    public void c_actualizarTurnoTest() throws ResourceNotFoundException, BadRequestException {
        Turno turno = turnoService.actualizar(
                new Turno(
                        1L,
                        pacienteService.buscar(1L).get(),
                        odontologoService.buscar(1L).get(),
                        LocalDate.of(2022,10,1)
                ));
        Assert.assertEquals(
                LocalDate.of(2022,10,1),turno.getFecha()
        );
    }

    @Test
    public void d_listarTurnoTest() {
        Assert.assertNotNull(turnoService.listar());
    }

    @Test
    public void e_eliminarTurnoTest() throws ResourceNotFoundException {
        turnoService.eliminar(1L);
        Assert.assertThrows(ResourceNotFoundException.class, () -> turnoService.buscar(1L));
    }
}
