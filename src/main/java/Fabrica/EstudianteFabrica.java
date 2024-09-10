/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Direccion;
import Usuario.Estudiante;
import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class EstudianteFabrica {
    
    public Estudiante crearEstudiante(int id, String codigo, String programa, double promedio, Persona persona){
        return new Estudiante(id, codigo, programa, promedio, persona);
    }
}
