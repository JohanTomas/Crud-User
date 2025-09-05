package pe.edu.vallegrande.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.model.Usuario;
import pe.edu.vallegrande.repository.UsuarioRepository;
import java.util.List;

@Service
public class UsuarioService {
    
    private UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    // Método con múltiples responsabilidades
    public Usuario guardar(Usuario usuario) {
        // Validaciones mezcladas con lógica de negocio
        if (usuario.getNombre().length() < 2) {
            throw new RuntimeException("Nombre muy corto");
        }
        
        // Lógica duplicada
        if (existeEmail(usuario.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        
        // Transformaciones que deberían estar en otro lugar
        usuario.setNombre(usuario.getNombre().trim());
        
        return usuarioRepository.guardar(usuario);
    }
    
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.obtenerTodos();
    }
    
    public Usuario obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.obtenerPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }
    
    public Usuario actualizar(Usuario usuario) {
        // Validaciones duplicadas
        if (usuario.getNombre().length() < 2) {
            throw new RuntimeException("Nombre muy corto");
        }
        
        Usuario existente = usuarioRepository.obtenerPorId(usuario.getId());
        if (existente == null) {
            throw new RuntimeException("Usuario no existe");
        }
        
        return usuarioRepository.actualizar(usuario);
    }
    
    public void eliminar(Long id) {
        Usuario existente = usuarioRepository.obtenerPorId(id);
        if (existente == null) {
            throw new RuntimeException("Usuario no existe");
        }
        usuarioRepository.eliminar(id);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existeEmail(email);
    }
}