package com.bootcampide.bootcampide.controlador;

import com.bootcampide.bootcampide.dominio.Persona;
import com.bootcampide.bootcampide.ejercitacion.ManoObra;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private List<Persona> personas = new ArrayList<>(
            Arrays.asList(
                    new Persona("mariano", "acosta", "111111111"),
                    new Persona("lucas", "gonzalez", "23516984"),
                    new Persona("lucia", "perez", "22222222"),
                    new Persona("mariana", "lopez", "33333333")
            )
    );

//    CRUD
    @GetMapping
    public ResponseEntity<?> getPersonas() {
        Map<String, Object> mensaje = new HashMap<>();
        if (this.personas.isEmpty()) {
            mensaje.put("mensaje", "No hay datos de personas");
        } else {
            mensaje.put("satisfactorio", Boolean.TRUE);
            mensaje.put("datos", this.personas);
        }
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
//    public List<Persona> getPersonas(){
//        return this.personas;
//    }

    @GetMapping("/{dni}")
    public Persona getPersonaByDni(@PathVariable String dni){
        return this.personas.stream()
                .filter(per -> per.getDni().equals(dni))
                .findFirst()
                .orElse(new Persona());
    }



    @GetMapping("/find")
    public Persona buscarPersona(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String apellido,
                                 @RequestParam(required = false) String dni){
        if (nombre == null && apellido == null && dni == null){
            return new Persona();
        }

        if (nombre != null && !nombre.isEmpty()){
            //buscar nombre
//            for (Persona per : this.personas){
//                if(per.getNombre().equals(nombre)){
//                    return per;
//                }
//            }
//             MAP PARA CONVERTIR O CASTEAR UN ELEMENTO A OTRO TIPO
//            FILTER PARA HACER BUSQUEDAS
//            List<Integer> dnis = this.personas.stream()
//                    .map(p -> Integer.parseInt(p.getDni()))
//                    .collect(Collectors.toList());

            return this.personas.stream()
                    .filter(per -> per.getNombre().equals(nombre))
                    .findFirst()
                    .orElse(new Persona("ramon", "acosta", "5555"));
        } else if (!apellido.isEmpty()) {
            //buscar apellido
        } else if (!dni.isEmpty()){
            //buscar dni
        }
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addPersona(@RequestBody Persona persona){
        HttpHeaders headers = new HttpHeaders();
        headers.set("alta-info", "Correcto");
        if (persona.getDni() != null && !persona.getDni().isEmpty()){
            this.personas.add(persona);
            return new ResponseEntity<>(persona, headers, HttpStatus.CREATED);
        }
        return ResponseEntity
                .badRequest()
                .body("No se pudo dar de alta");
        // return new ResponseEntity<>("No se pudo dar de alta", HttpStatus.BAD_REQUEST);
    }
//    public Persona addPersona(@RequestBody Persona persona){
//        if (persona.getDni() != null && !persona.getDni().isEmpty()) {
//            this.personas.add(persona);
//        }
//        return persona;
//    }

    @PutMapping("/{dni}")
    public Persona updatePersona(@PathVariable String dni,
                                 @RequestBody Persona persona){
        //int indice = 0;
        Persona personaModificar = this.personas.stream()
                        .filter(p -> p.getDni().equals(dni))
                        .findFirst()
                        .orElse(null);

        if (personaModificar != null){
            personaModificar.setNombre(persona.getNombre());
            personaModificar.setApellido(persona.getApellido());
            this.personas.set(this.personas.indexOf(personaModificar), personaModificar);
        }
        return new Persona();
    }

    @DeleteMapping("/{dni}")
    public void deletePersona(@PathVariable String dni){
        Persona personaBorrar = this.personas.stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
        // si no existe no hago nada
        this.personas.remove(personaBorrar);
    }



}
