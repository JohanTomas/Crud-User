package pe.edu.vallegrande.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.model.Usuario;
import pe.edu.vallegrande.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = new Usuario("Juan Perez", "juan@email.com", 25);
        when(usuarioRepository.existeEmail("juan@email.com")).thenReturn(false);
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.guardar(usuario);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        verify(usuarioRepository).guardar(usuario);
    }

    @Test
    void testGuardarUsuarioEmailDuplicado() {
        Usuario usuario = new Usuario("Juan Perez", "juan@email.com", 25);
        when(usuarioRepository.existeEmail("juan@email.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> usuarioService.guardar(usuario));
    }

    @Test
    void testObtenerTodos() {
        List<Usuario> usuarios = Arrays.asList(
            new Usuario("Juan", "juan@email.com", 25),
            new Usuario("Maria", "maria@email.com", 30)
        );
        when(usuarioRepository.obtenerTodos()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(usuarioRepository).obtenerTodos();
    }

    @Test
    void testObtenerPorId() {
        Usuario usuario = new Usuario("Juan", "juan@email.com", 25);
        usuario.setId(1L);
        when(usuarioRepository.obtenerPorId(1L)).thenReturn(usuario);

        Usuario resultado = usuarioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(usuarioRepository.obtenerPorId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> usuarioService.obtenerPorId(1L));
    }
}