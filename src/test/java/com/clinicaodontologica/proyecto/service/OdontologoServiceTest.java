package com.clinicaodontologica.proyecto.service;
import com.clinicaodontologica.proyecto.entities.Odontologo;
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


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;


    public void cargarDatos(){

        odontologoService.guardar(
                new Odontologo(
                        "2343424",
                        "Este",
                        "la")
        );
    }

    @Test
    public void b_buscarOdontologoTest() throws ResourceNotFoundException {
        Assert.assertNotNull(odontologoService.buscar(1L));
    }

    @Test
    public void a_registrarOdontologoTest() throws BadRequestException, ResourceNotFoundException {
        this.cargarDatos();
        Odontologo odontologo = odontologoService.guardar(
                odontologoService.buscar(1L).get());
        Assert.assertNotNull(odontologo);
    }

    //EL ASSERT DEPENDE DE LO QUE QUIERA MODIFICAR; EN ESTE CASO LA FECHA
    @Test
    public void c_actualizarOdontologoTest() throws BadRequestException, ResourceNotFoundException {
        Odontologo odontologo = odontologoService.actualizar(
                new Odontologo(1L, "12345")
        );
        Assert.assertEquals(
                "12345", odontologo.getMatricula()
        );
    }

    @Test
    public void d_listarOdontologoTest() {
        Assert.assertNotNull(odontologoService.listar());
    }

    @Test
    public void e_eliminarOdontologoTest() throws ResourceNotFoundException {
        odontologoService.eliminar(1L);
        Assert.assertThrows(ResourceNotFoundException.class, () -> odontologoService.buscar(1L));
    }
}
