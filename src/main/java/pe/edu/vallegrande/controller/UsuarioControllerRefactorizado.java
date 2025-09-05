package pe.edu.vallegrande.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.model.UsuarioRefactorizado;
import pe.edu.vallegrande.service.UsuarioServiceRefactorizado;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v2/usuarios")
@RequiredArgsConstructor
public class UsuarioControllerRefactorizado {
    
    private final UsuarioServiceRefactorizado usuarioService;
    
    @PostMapping
    public ResponseEntity<UsuarioRefactorizado> crear(@Valid @RequestBody UsuarioRefactorizado usuario) {
        log.info("Solicitud para crear usuario con email: {}", usuario.getEmail());
        
        UsuarioRefactorizado usuarioCreado = usuarioService.crear(usuario);
        
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioRefactorizado>> obtenerTodos() {
        log.debug("Solicitud para obtener todos los usuarios");
        
        List<UsuarioRefactorizado> usuarios = usuarioService.obtenerTodos();
        
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRefactorizado> obtenerPorId(@PathVariable Long id) {
        log.debug("Solicitud para obtener usuario con ID: {}", id);
        
        UsuarioRefactorizado usuario = usuarioService.obtenerPorId(id);
        
        return ResponseEntity.ok(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRefactorizado> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioRefactorizado usuario) {
        
        log.info("Solicitud para actualizar usuario con ID: {}", id);
        
        UsuarioRefactorizado usuarioActualizado = usuarioService.actualizar(id, usuario);
        
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar usuario con ID: {}", id);
        
        usuarioService.eliminar(id);
        
        return ResponseEntity.noContent().build();
    }
}