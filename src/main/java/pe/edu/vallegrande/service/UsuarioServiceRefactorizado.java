package pe.edu.vallegrande.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.exception.EmailDuplicadoException;
import pe.edu.vallegrande.exception.UsuarioNotFoundException;
import pe.edu.vallegrande.model.UsuarioRefactorizado;
import pe.edu.vallegrande.repository.UsuarioRepositoryRefactorizado;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceRefactorizado {
    
    private final UsuarioRepositoryRefactorizado usuarioRepository;
    private final UsuarioValidationService validationService;
    
    public UsuarioRefactorizado crear(UsuarioRefactorizado usuario) {
        log.info("Creando usuario con email: {}", usuario.getEmail());
        
        validationService.validarUsuarioParaCreacion(usuario);
        usuario.normalizar();
        
        UsuarioRefactorizado usuarioGuardado = usuarioRepository.guardar(usuario);
        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());
        
        return usuarioGuardado;
    }
    
    public List<UsuarioRefactorizado> obtenerTodos() {
        log.debug("Obteniendo todos los usuarios");
        return usuarioRepository.obtenerTodos();
    }
    
    public UsuarioRefactorizado obtenerPorId(Long id) {
        log.debug("Obteniendo usuario por ID: {}", id);
        return usuarioRepository.obtenerPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(
                    String.format("Usuario con ID %d no encontrado", id)));
    }
    
    public UsuarioRefactorizado actualizar(Long id, UsuarioRefactorizado usuario) {
        log.info("Actualizando usuario con ID: {}", id);
        
        UsuarioRefactorizado usuarioExistente = obtenerPorId(id);
        validationService.validarUsuarioParaActualizacion(usuario, usuarioExistente);
        
        usuario.setId(id);
        usuario.normalizar();
        
        UsuarioRefactorizado usuarioActualizado = usuarioRepository.actualizar(usuario);
        log.info("Usuario actualizado exitosamente");
        
        return usuarioActualizado;
    }
    
    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        obtenerPorId(id); // Verificar que existe
        usuarioRepository.eliminar(id);
        
        log.info("Usuario eliminado exitosamente");
    }
}