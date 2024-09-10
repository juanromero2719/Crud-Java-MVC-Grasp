/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.EstudianteFabrica;
import Repositorio.EstudianteDAO;
import Usuario.Empleado;
import Usuario.Estudiante;
import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class EstudianteController {
    
    private final EstudianteDAO estudianteDAO;
    private final EstudianteFabrica estudianteFabrica;
    
    public EstudianteController(){
        this.estudianteDAO = new EstudianteDAO();
        this.estudianteFabrica = new EstudianteFabrica();
    }
    
    public void agregar(int id, String codigo, String programa, double promedio, Persona persona) {
        Estudiante estudiante = estudianteFabrica.crearEstudiante(id, codigo, programa, promedio, persona);
        estudianteDAO.crear(estudiante);
    }

    public Estudiante consultar(int id) {
        return estudianteDAO.leer(id);
    }

    public void actualizar(int id, String codigo, String programa, double promedio, Persona persona) {
        Estudiante estudiante = estudianteFabrica.crearEstudiante(id, codigo, programa, promedio, persona);
        estudianteDAO.actualizar(estudiante);
    }

    public void eliminar(int id) {
        estudianteDAO.eliminar(id);
    }

    public void listar() {
        estudianteDAO.listar();
    }
    
     public void guardarEnArchivoCSV(Estudiante estudiante) {
        estudianteDAO.guardarEnArchivoCSV(estudiante);
    }

    public void leerDesdeArchivoCSV() {
        estudianteDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Estudiante estudiante) {
        estudianteDAO.guardarEnArchivoBIN(estudiante);
    }

    public void leerDesdeArchivoBIN() {
        estudianteDAO.leerDesdeArchivoBIN();
    }
}
