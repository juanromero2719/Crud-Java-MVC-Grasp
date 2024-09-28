/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.PersonaServicio;
import Usuario.Persona;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class PersonaController {
    
    private final PersonaServicio personaServicio;
    
    public PersonaController(){
        this.personaServicio = new PersonaServicio();
    }
       
    public void agregarPersona(int idPersona, String nombres, String apellidos, int idDireccion) {
        personaServicio.agregarPersona(idPersona, nombres, apellidos, idDireccion);
    }

    public Persona consultarPersona(int idPersona) {
        return personaServicio.consultarPersona(idPersona);
    }

    public void actualizarPersona(int idPersona, String nombres, String apellidos, int idDireccion) {
        personaServicio.actualizarPersona(idPersona, nombres, apellidos, idDireccion);
    }

    public void eliminarPersona(int idPersona) {
        personaServicio.eliminarPersona(idPersona);
    }

    public List<Persona> listarPersonas() {
        return personaServicio.listarPersonas();
    }    
}
