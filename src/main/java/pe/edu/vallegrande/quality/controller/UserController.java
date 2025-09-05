
package pe.edu.vallegrande.quality.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.*;

// Falta @RestControllerAdvice para errores, rutas inconsistentes, nombres poco claros
@RestController
public class UserController {

    // Inyección por campo (mala práctica).
    @SuppressWarnings("all")
    public UserService service = new UserService();

    @GetMapping("/listAll")
    public List<User> a(){
        // Lógica de presentación mezclada con negocio
        System.out.println("Getting users..."); // mala práctica de logging
        List<User> users = service.getAll();
        if(users == null){
            users = new ArrayList<>();
        }
        // Validación innecesaria y duplicada
        if(users.size() == 0){
            return users;
        }
        return users;
    }

    @PostMapping("/createUserNow")
    public Object b(@RequestBody Map payload){
        // Validaciones dentro del controller, tipos sin genéricos
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        Integer age = payload.get("age") == null ? null : (Integer) payload.get("age");
        if(name == null || name.equals("")){
            return "name is required"; // Respuesta no tipada
        }
        User u = new User(null, name, email, age);
        try{
            return service.create(u);
        }catch(Exception e){
            e.printStackTrace(); // mala práctica
            return e.getMessage();
        }
    }

    @GetMapping("/user/{id}")
    public Object getOne(@PathVariable String id){
        Optional<User> u = service.find(id);
        if(u.isPresent()){
            return u.get();
        }
        return "not found";
    }

    @DeleteMapping("/del/{id}")
    public String delete(@PathVariable("id") String identifier){
        boolean ok = service.remove(identifier);
        if(ok){
            return "ok";
        }
        return "fail";
    }
}
