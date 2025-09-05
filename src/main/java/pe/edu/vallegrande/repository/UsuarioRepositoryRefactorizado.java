package pe.edu.vallegrande.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.model.UsuarioRefactorizado;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class UsuarioRepositoryRefactorizado {
    
    private final Map<Long, UsuarioRefactorizado> usuarios = new ConcurrentHashMap<>();
    private final Map<String, Long> emailIndex = new ConcurrentHashMap<>();
    private final AtomicLong contador = new AtomicLong(1L);
    
    public UsuarioRefactorizado guardar(UsuarioRefactorizado usuario) {
        Long id = contador.getAndIncrement();
        usuario.setId(id);
        
        usuarios.put(id, usuario);
        emailIndex.put(usuario.getEmail(), id);
        
        log.debug("Usuario guardado con ID: {}", id);
        return usuario;
    }
    
    public List<UsuarioRefactorizado> obtenerTodos() {
        return new ArrayList<>(usuarios.values());
    }
    
    public Optional<UsuarioRefactorizado> obtenerPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }
    
    public UsuarioRefactorizado actualizar(UsuarioRefactorizado usuario) {
        Long id = usuario.getId();
        UsuarioRefactorizado usuarioExistente = usuarios.get(id);
        
        if (usuarioExistente != null) {
            // Actualizar índice de email si cambió
            if (!usuarioExistente.getEmail().equals(usuario.getEmail())) {
                emailIndex.remove(usuarioExistente.getEmail());
                emailIndex.put(usuario.getEmail(), id);
            }
            
            usuarios.put(id, usuario);
            log.debug("Usuario actualizado con ID: {}", id);
        }
        
        return usuario;
    }
    
    public void eliminar(Long id) {
        UsuarioRefactorizado usuario = usuarios.remove(id);
        if (usuario != null) {
            emailIndex.remove(usuario.getEmail());
            log.debug("Usuario eliminado con ID: {}", id);
        }
    }
    
    public boolean existeEmail(String email) {
        return emailIndex.containsKey(email);
    }
    
    public Optional<UsuarioRefactorizado> obtenerPorEmail(String email) {
        Long id = emailIndex.get(email);
        return id != null ? Optional.ofNullable(usuarios.get(id)) : Optional.empty();
    }
}