
package pe.edu.vallegrande.quality.service;

import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;
import java.util.*;

public class UserService {

    // Inyección por campo (mala práctica) y sin @Service.
    public UserRepository repo = new UserRepository();

    public List<User> getAll(){
        List<User> data = repo.getUsers();
        if(data == null){ // chequeo innecesario
            return new ArrayList<>();
        }
        // Duplicación de lógica: ordenamiento manual aquí
        Collections.sort(data, (a,b) -> a.name != null ? a.name.compareToIgnoreCase(b.name) : -1);
        return data;
    }

    public User create(User u){
        if(u == null){
            throw new RuntimeException("user is null"); // excepciones genéricas
        }
        // Validaciones manuales repetidas
        if(u.name == null || u.name.trim().equals("")){
            throw new RuntimeException("name required");
        }
        if(u.email == null || !u.email.contains("@")){
            throw new RuntimeException("email invalid");
        }
        if(u.age == null){
            u.age = 0; // valor mágico
        }
        if(u.id == null || u.id.isEmpty()){
            u.id = UUID.randomUUID().toString();
        }
        return repo.save(u);
    }

    public Optional<User> find(String id){
        return repo.findById(id);
    }

    public boolean remove(String id){
        return repo.delete(id);
    }

    // Utilidad duplicada que debería extraerse o usarse de forma consistente
    public List<User> sortByName(List<User> users){
        Collections.sort(users, (x,y) -> x.name.compareToIgnoreCase(y.name));
        return users;
    }
}
