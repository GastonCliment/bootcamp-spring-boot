package com.bootcampide.bootcampide.controlador;

import com.bootcampide.bootcampide.dominio.Persona;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class HolaMundoController {


    @GetMapping("/saludar/{nombre}")
    public String saludarConAtributos(@PathVariable("nombre") String nombreCompleto){
//        String str = "Hola " + nombre + ", ¿como estas?"; es lo mismo que lo de abajo
        boolean sonLetras = nombreCompleto.matches("^[a-zA-Z]+$");
        if (!sonLetras){
            return "El nombre debe contener letras";
        }
        return "Hola ".concat(nombreCompleto)
                .concat(", ¿como estas?");
    }

    @GetMapping("/saludar-rp/")
    public String saludarConRequestParam(@RequestParam String nombre){
        return "Hola ".concat(nombre).concat(", como estas?. \nBy RequestParam");
    }

    @GetMapping()
    public String saludar(){
        return "Hola Mundo";
    }

    @GetMapping("/datos/{nombre}/{apellido}")
    public String leerDatos(@PathVariable String nombre,
                            @PathVariable String apellido){
        return "Datos leidos: " + "\n" +
                "Nombre: " + nombre +
                "\nApellido: " + apellido;
    }

    @GetMapping("/objeto/{nombre}/{apellido}")
    public Persona leerDatosParaObj(@PathVariable String nombre,
                                    @PathVariable String apellido){
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        return persona;
    }

    @GetMapping("calcular/{valorA}/{valorB}")
    public Double multiplicar(@PathVariable double valorA,
                              @PathVariable double valorB){
        return valorA * valorB;
    }

    @GetMapping("calcular/suma")
    public Double sumar(@RequestParam(name = "valor1", required = false) Double valorA,
                        @RequestParam(name = "valor2", required = false) Double valorB){
        if(this.validarNumero(valorA)){
            return 0.0;
        }
        return valorA + valorB;
    }

    private boolean validarNumero(Double valor){
        if(valor == null || valor < 0.0){
            return false;
        }
        return true;
    }

    @GetMapping("/find-datos")
    public String buscarDatos(@RequestParam(required = false) String nombre,
                              @RequestParam(required = false) Optional<String> apellido){

//        Optional<Persona> persona = Optional.of(new Persona());

//        if(nombre == null){
//            return "El nombre no puede estar vacio";
//        }

        if(!apellido.isPresent()){
            return "El apellido no puede estar vacio";
        }

        return "Nombre: " + nombre +
                " Apellido: " + apellido.orElse("valor vacio");

    }

}
