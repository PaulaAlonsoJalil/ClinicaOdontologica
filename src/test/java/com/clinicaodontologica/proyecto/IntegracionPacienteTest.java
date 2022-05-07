package com.clinicaodontologica.proyecto;

import com.clinicaodontologica.proyecto.entities.Domicilio;
import com.clinicaodontologica.proyecto.entities.Paciente;
import com.clinicaodontologica.proyecto.exceptions.BadRequestException;
import com.clinicaodontologica.proyecto.exceptions.ResourceNotFoundException;
import com.clinicaodontologica.proyecto.service.PacienteService;
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
public class IntegracionPacienteTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteService pacienteService;


    public void cargarDatos() {
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
    public void d_listarPacientes() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/pacientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void b_buscarPaciente() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void a_guardarPaciente() throws Exception {
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .content(asJsonString(new Paciente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void c_actualizarPaciente() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.put("/pacientes")
                        .content(asJsonString(new Paciente(1L,LocalDate.of(2021,1,2))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaIngreso").value("2021-01-02"))
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void e_eliminarPaciente() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals("Se elimino al paciente con id= 1", respuesta.getResponse().getContentAsString());
    }

}