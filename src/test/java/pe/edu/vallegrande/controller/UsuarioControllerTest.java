package pe.edu.vallegrande.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.vallegrande.model.Usuario;
import pe.edu.vallegrande.service.UsuarioService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearUsuario() throws Exception {
        Usuario usuario = new Usuario("Juan Perez", "juan@email.com", 25);
        usuario.setId(1L);
        
        when(usuarioService.existeEmail("juan@email.com")).thenReturn(false);
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(usuarioService.obtenerTodos()).thenReturn(Arrays.asList(
            new Usuario("Juan", "juan@email.com", 25),
            new Usuario("Maria", "maria@email.com", 30)
        ));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId() throws Exception {
        Usuario usuario = new Usuario("Juan", "juan@email.com", 25);
        usuario.setId(1L);
        when(usuarioService.obtenerPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
}