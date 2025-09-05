package pe.edu.vallegrande.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.exception.EmailDuplicadoException;
import pe.edu.vallegrande.model.UsuarioRefactorizado;
import pe.edu.vallegrande.repository.UsuarioRepositoryRefactorizado;

@Service
@RequiredArgsConstructor
public class UsuarioValidationService {
    
    private final UsuarioRepositoryRefactorizado usuarioRepository;
    
    public void validarUsuarioParaCreacion(UsuarioRefactorizado usuario) {
        validarDatosBasicos(usuario);
        validarEmailUnico(usuario.getEmail());
    }
    
    public void validarUsuarioParaActualizacion(UsuarioRefactorizado usuario, UsuarioRefactorizado usuarioExistente) {
        validarDatosBasicos(usuario);
        
        // Solo validar email único si cambió
        if (!usuario.getEmail().equals(usuarioExistente.getEmail())) {
            validarEmailUnico(usuario.getEmail());
        }
    }
    
    private void validarDatosBasicos(UsuarioRefactorizado usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (usuario.getNombre().trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        
        if (!isValidEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
        
        if (usuario.getEdad() == null || usuario.getEdad() <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor a 0");
        }
        
        if (usuario.getEdad() > 120) {
            throw new IllegalArgumentException("La edad debe ser menor a 120 años");
        }
    }
    
    private void validarEmailUnico(String email) {
        if (usuarioRepository.existeEmail(email)) {
            throw new EmailDuplicadoException(
                String.format("Ya existe un usuario con el email: %s", email));
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}