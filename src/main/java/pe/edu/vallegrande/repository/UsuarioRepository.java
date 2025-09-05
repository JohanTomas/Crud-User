package pe.edu.vallegrande.repository;

import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.model.Usuario;
import java.util.*;

@Repository
public class UsuarioRepository {
    
    // Simulación de base de datos en memoria con problemas
    private Map<Long, Usuario> usuarios = new HashMap<>();
    private Long contador = 1L;
    
    public Usuario guardar(Usuario usuario) {
        usuario.setId(contador++);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }
    
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios.values());
    }
    
    public Usuario obtenerPorId(Long id) {
        return usuarios.get(id);
    }
    
    public Usuario actualizar(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }
    
    public void eliminar(Long id) {
        usuarios.remove(id);
    }
    
    // Método ineficiente para verificar email
    public boolean existeEmail(String email) {
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}