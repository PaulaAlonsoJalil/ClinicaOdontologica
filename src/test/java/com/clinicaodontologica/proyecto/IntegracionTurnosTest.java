package com.clinicaodontologica.proyecto;

import com.clinicaodontologica.proyecto.entities.Domicilio;
import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.entities.Turno;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.service.OdontologoService;
import com.clinicaodontologica.proyecto.service.PacienteService;
import com.clinicaodontologica.proyecto.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnosTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    TurnoService turnoService;

    public void cargarDatos() throws BadRequestException, ResourceNotFoundException {
        Domicilio domicilio = new Domicilio(
                "Imaginaria",
                "27",
                "Irlanda",
                "Argentina"
        );
        pacienteService.guardar(
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
        turnoService.guardar(
                new Turno(
                        pacienteService.buscar(1L).get(),
                        odontologoService.buscar(1L).get(),
                        LocalDate.of(2022,06,10)
                )
        );
    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void c_listarTurnos() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void b_buscarTurno() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/turnos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void a_guardarTurno() throws Exception {
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                .content(asJsonString(new Turno(pacienteService.guardar(new Paciente()), odontologoService.guardar(new Odontologo()), LocalDate.of(2020,1,8))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void d_actualizarTurno() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .content(asJsonString(new Turno(1L ,pacienteService.actualizar(new Paciente(1L,LocalDate.of(2021,1,2))), odontologoService.guardar(new Odontologo(1L, "ASDADSA")), LocalDate.of(2020,1,8))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fecha").value("2020-01-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.odontologo.matricula").value("ASDADSA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente.fechaIngreso").value("2021-01-02"))
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void e_eliminarTurno() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals("Se elimino al turno con id= 1", respuesta.getResponse().getContentAsString());
    }

}
