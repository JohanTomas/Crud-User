package pe.edu.vallegrande.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.exception.UsuarioNotFoundException;
import pe.edu.vallegrande.model.UsuarioRefactorizado;
import pe.edu.vallegrande.repository.UsuarioRepositoryRefactorizado;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceRefactorizadoTest {

    @Mock
    private UsuarioRepositoryRefactorizado usuarioRepository;

    @Mock
    private UsuarioValidationService validationService;

    @InjectMocks
    private UsuarioServiceRefactorizado usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        UsuarioRefactorizado usuario = UsuarioRefactorizado.builder()
                .nombre("Juan Perez")
                .email("juan@email.com")
                .edad(25)
                .build();

        when(usuarioRepository.guardar(any(UsuarioRefactorizado.class))).thenReturn(usuario);

        UsuarioRefactorizado resultado = usuarioService.crear(usuario);

        assertNotNull(resultado);
        verify(validationService).validarUsuarioParaCreacion(usuario);
        verify(usuarioRepository).guardar(usuario);
    }

    @Test
    void testObtenerTodos() {
        List<UsuarioRefactorizado> usuarios = Arrays.asList(
            UsuarioRefactorizado.builder().nombre("Juan").email("juan@email.com").edad(25).build(),
            UsuarioRefactorizado.builder().nombre("Maria").email("maria@email.com").edad(30).build()
        );
        when(usuarioRepository.obtenerTodos()).thenReturn(usuarios);

        List<UsuarioRefactorizado> resultado = usuarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(usuarioRepository).obtenerTodos();
    }

    @Test
    void testObtenerPorId() {
        UsuarioRefactorizado usuario = UsuarioRefactorizado.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@email.com")
                .edad(25)
                .build();
        
        when(usuarioRepository.obtenerPorId(1L)).thenReturn(Optional.of(usuario));

        UsuarioRefactorizado resultado = usuarioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository).obtenerPorId(1L);
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(usuarioRepository.obtenerPorId(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.obtenerPorId(1L));
    }

    @Test
    void testActualizar() {
        UsuarioRefactorizado usuarioExistente = UsuarioRefactorizado.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@email.com")
                .edad(25)
                .build();

        UsuarioRefactorizado usuarioActualizado = UsuarioRefactorizado.builder()
                .nombre("Juan Carlos")
                .email("juan.carlos@email.com")
                .edad(26)
                .build();

        when(usuarioRepository.obtenerPorId(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.actualizar(any(UsuarioRefactorizado.class))).thenReturn(usuarioActualizado);

        UsuarioRefactorizado resultado = usuarioService.actualizar(1L, usuarioActualizado);

        assertNotNull(resultado);
        verify(validationService).validarUsuarioParaActualizacion(usuarioActualizado, usuarioExistente);
        verify(usuarioRepository).actualizar(usuarioActualizado);
    }

    @Test
    void testEliminar() {
        UsuarioRefactorizado usuario = UsuarioRefactorizado.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@email.com")
                .edad(25)
                .build();

        when(usuarioRepository.obtenerPorId(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.eliminar(1L));

        verify(usuarioRepository).obtenerPorId(1L);
        verify(usuarioRepository).eliminar(1L);
    }
}