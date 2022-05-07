package com.clinicaodontologica.proyecto;
import com.clinicaodontologica.proyecto.entities.Odontologo;
import com.clinicaodontologica.proyecto.service.OdontologoService;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionOdontologoTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    OdontologoService odontologoService;


    public void cargarDatos() {
        odontologoService.guardar(
                new Odontologo(
                        "2343424",
                        "Este",
                        "la")
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
    public void d_listarOdontologos() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/odontologos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void b_buscarOdontologo() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void a_guardarOdontologo() throws Exception {
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .content(asJsonString(new Odontologo()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void c_actualizarOdontologo() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.put("/odontologos")
                        .content(asJsonString(new Odontologo(1L,"adasdasd")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("adasdasd"))
                .andReturn();
        Assertions.assertNotNull(respuesta.getResponse().getContentAsString());
    }
    @Test
    public void e_eliminarOdontologo() throws Exception {
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals("Se elimino al odontologo con id= 1", respuesta.getResponse().getContentAsString());
    }

}