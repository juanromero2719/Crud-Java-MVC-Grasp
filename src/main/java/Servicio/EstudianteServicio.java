/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Fabrica.ConexionFabrica;
import Fabrica.EstudianteFabrica;
import Interface.IGestorDatos;
import Usuario.Estudiante;
import Usuario.Persona;
import java.util.List;
/**
 *
 * @author wrydmoon
 */
public class EstudianteServicio {
    
    private final  IGestorDatos<Persona>  personaDAO;
    private final IGestorDatos<Estudiante> estudianteDAO;
    private final EstudianteFabrica estudianteFabrica;

    public EstudianteServicio() {
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
        this.estudianteDAO = ConexionFabrica.getGestorDatos(Estudiante.class);
        this.estudianteFabrica = new EstudianteFabrica();
    }

    public void agregarEstudiante(int idEstudiante, String codigo, String programa, double promedio, int idPersona) {
        
        Persona persona = personaDAO.consultar(idPersona);
        
        if(persona != null){
            Estudiante estudiante = estudianteFabrica.crearEstudiante(idEstudiante, codigo, programa, promedio, persona);
            estudianteDAO.crear(estudiante);
        }else{
            System.out.println("Persona no encontrada");
        }
        
    }

    public Estudiante consultarEstudiante(int idEstudiante) {
        Estudiante estudiante = estudianteDAO.consultar(idEstudiante);
        
        if(estudiante != null){
            System.out.println(estudiante.toString());
            return estudiante;
        }
        
        System.out.println("Estudiante no encontrado");
        return null;
    }

    public void actualizarEstudiante(int idEstudiante, String codigo, String programa, double promedio, int idPersona) {
        
        Persona persona = personaDAO.consultar(idPersona);
        
        if(persona != null){
            Estudiante estudiante = estudianteFabrica.crearEstudiante(idEstudiante, codigo, programa, promedio, persona);
            estudianteDAO.actualizar(estudiante);
        }else{
            System.out.println("Persona no encontrada");
        }
        
    }

    public void eliminarEstudiante(int idEstudiante) {
        estudianteDAO.eliminar(idEstudiante);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.listar();
    }
   
}
