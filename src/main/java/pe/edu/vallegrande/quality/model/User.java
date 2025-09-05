
package pe.edu.vallegrande.quality.model;

public class User {
    // Campos públicos (mala práctica).
    public String id;
    public String name;
    public String email;
    public Integer age;

    // Falta validación, equals/hashCode, toString, etc.
    public User(){}

    public User(String id, String name, String email, Integer age){
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
