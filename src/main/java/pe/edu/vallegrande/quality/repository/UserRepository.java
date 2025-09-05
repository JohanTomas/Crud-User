
package pe.edu.vallegrande.quality.repository;

import pe.edu.vallegrande.quality.model.User;
import java.util.*;

public class UserRepository {
    // Lista en memoria sin sincronización ni persistencia real.
    private static List<User> l = new ArrayList<>();

    public List<User> getUsers(){
        return l; // Exposición directa de la lista interna.
    }

    public User save(User u){
        // Reglas duplicadas, sin validación real.
        l.add(u);
        return u;
    }

    public Optional<User> findById(String id){
        for(User u : l){
            if(u.id != null && u.id.equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public boolean delete(String id){
        // Borrado ineficiente.
        Iterator<User> it = l.iterator();
        while(it.hasNext()){
            User u = it.next();
            if(u.id != null && u.id.equals(id)){
                it.remove();
                return true;
            }
        }
        return false;
    }
}
