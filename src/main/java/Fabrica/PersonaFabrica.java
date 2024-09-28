/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Direccion;
import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class PersonaFabrica {
    
    public Persona crearPersona(int idPersona, String nombres, String apellidos, Direccion direccion){
        return new Persona(idPersona, nombres, apellidos, direccion);
    }
    
}
