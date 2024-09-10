/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.EstudianteController;
import Controlador.PersonaController;
import Usuario.Estudiante;
import Usuario.Persona;
/**
 *
 * @author wrydmoon
 */
public class EstudianteServicio {
    
    private final PersonaController personaController;
    private final EstudianteController estudianteController;

    public EstudianteServicio() {
        this.personaController = new PersonaController();
        this.estudianteController = new EstudianteController();
    }

    public void agregarEstudiante(int id, String codigo, String programa, double promedio, int idPersona) {
        
        Persona persona = personaController.consultar(idPersona);
        
        if(persona != null){
            estudianteController.agregar(id, codigo, programa, promedio, persona);
        }else{
            System.out.println("Persona no encontrada");
        }
        
    }

    public Estudiante consultarEstudiante(int id) {
        return estudianteController.consultar(id);
    }

    public void actualizarEstudiante(int id, String codigo, String programa, double promedio, int idPersona) {
        
        Persona persona = personaController.consultar(idPersona);
        
        if(persona != null){
            estudianteController.actualizar(id, codigo, programa, promedio, persona);
        }else{
            System.out.println("Persona no encontrada");
        }
        
    }

    public void eliminarEstudiante(int id) {
        estudianteController.eliminar(id);
    }

    public void listarEstudiantes() {
        estudianteController.listar();
    }

    public void guardarEnArchivoCSV(int id) {
        Estudiante estudiante = estudianteController.consultar(id);
        if (estudiante != null) {
            estudianteController.guardarEnArchivoCSV(estudiante);
        } else {
            throw new IllegalArgumentException("Estudiante no encontrado.");
        }
    }

    public void leerDesdeArchivoCSV() {
        estudianteController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(int id) {
        Estudiante estudiante = estudianteController.consultar(id);
        if (estudiante != null) {
            estudianteController.guardarEnArchivoBIN(estudiante);
        } else {
            throw new IllegalArgumentException("Estudiante no encontrado.");
        }
    }

    public void leerDesdeArchivoBIN() {
        estudianteController.leerDesdeArchivoBIN();
    }
}
