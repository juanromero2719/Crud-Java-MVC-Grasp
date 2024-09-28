/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Servicio.EstudianteServicio;
import Usuario.Estudiante;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class EstudianteController {
    
    private final EstudianteServicio estudianteServicio;
   
    public EstudianteController(){
        this.estudianteServicio = new EstudianteServicio();
    }
    
    public void agregarEstudiante(int idEstudiante, String codigo, String programa, double promedio, int idPersona) {
        estudianteServicio.agregarEstudiante(idEstudiante, codigo, programa, promedio, idPersona);
    }

    public Estudiante consultarEstudiante(int idEstudiante) {
        return estudianteServicio.consultarEstudiante(idEstudiante);
    }

    public void actualizarEstudiante(int idEstudiante, String codigo, String programa, double promedio, int idPersona) {
        estudianteServicio.actualizarEstudiante(idEstudiante, codigo, programa, promedio, idPersona);
    }

    public void eliminarEstudiante(int idEstudiante) {
        estudianteServicio.eliminarEstudiante(idEstudiante);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteServicio.listarEstudiantes();
    }
      
}
