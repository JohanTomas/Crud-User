package pe.edu.vallegrande.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.model.Usuario;
import pe.edu.vallegrande.service.UsuarioService;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    // Método con múltiples responsabilidades y sin validaciones
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario u) {
        // Validación manual duplicada
        if (u.getNombre() == null || u.getNombre().equals("")) {
            throw new RuntimeException("Nombre es requerido");
        }
        if (u.getEmail() == null || u.getEmail().equals("")) {
            throw new RuntimeException("Email es requerido");
        }
        if (u.getEdad() <= 0) {
            throw new RuntimeException("Edad debe ser mayor a 0");
        }
        
        // Lógica de negocio mezclada con controlador
        if (usuarioService.existeEmail(u.getEmail())) {
            throw new RuntimeException("Email ya existe");
        }
        
        // Transformación de datos en controlador
        u.setNombre(u.getNombre().trim().toUpperCase());
        u.setEmail(u.getEmail().toLowerCase());
        
        return usuarioService.guardar(u);
    }
    
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }
    
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario u) {
        // Duplicación de validaciones
        if (u.getNombre() == null || u.getNombre().equals("")) {
            throw new RuntimeException("Nombre es requerido");
        }
        if (u.getEmail() == null || u.getEmail().equals("")) {
            throw new RuntimeException("Email es requerido");
        }
        if (u.getEdad() <= 0) {
            throw new RuntimeException("Edad debe ser mayor a 0");
        }
        
        u.setId(id);
        u.setNombre(u.getNombre().trim().toUpperCase());
        u.setEmail(u.getEmail().toLowerCase());
        
        return usuarioService.actualizar(u);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}