package pe.edu.vallegrande.model;

// Clase sin encapsulación adecuada y con campos públicos
public class Usuario {
    public Long id;
    public String nombre;
    public String email;
    public int edad;
    public String telefono;
    
    // Constructor vacío
    public Usuario() {}
    
    // Constructor con parámetros sin validaciones
    public Usuario(String n, String e, int ed) {
        this.nombre = n;
        this.email = e;
        this.edad = ed;
    }
    
    // Getters y setters sin validaciones
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    // Método toString sin formato adecuado
    public String toString() {
        return "Usuario: " + nombre + " - " + email + " - " + edad;
    }
    
    // Método equals mal implementado
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario u = (Usuario) obj;
            return this.email.equals(u.email);
        }
        return false;
    }
}