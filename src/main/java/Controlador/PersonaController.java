/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.PersonaFabrica;
import Repositorio.PersonaDAO;
import Ubicacion.Direccion;

import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class PersonaController {
    
    private final PersonaDAO personaDAO;
    private final PersonaFabrica personaFabrica;
    
    public PersonaController(){
        this.personaDAO = new PersonaDAO();
        this.personaFabrica = new PersonaFabrica();
    }
       
    public void agregar(int id, String nombres, String apellidos, Direccion direccion) {
        Persona persona = personaFabrica.crearPersona(id, nombres, apellidos, direccion);
        personaDAO.crear(persona);
    }

    public Persona consultar(int id) {
        return personaDAO.leer(id);
    }

    public void actualizar(int id, String nombres, String apellidos, Direccion direccion) {
        Persona persona = personaFabrica.crearPersona(id, nombres, apellidos, direccion);
        personaDAO.actualizar(persona);
    }

    public void eliminar(int id) {
        personaDAO.eliminar(id);
    }

    public void listar() {
        personaDAO.listar();
    }
    
    public void guardarEnArchivoCSV(Persona persona) {
        personaDAO.guardarEnArchivoCSV(persona);
    }

    public void leerDesdeArchivoCSV() {
        personaDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Persona persona) {
        personaDAO.guardarEnArchivoBIN(persona);
    }

    public void leerDesdeArchivoBIN() {
        personaDAO.leerDesdeArchivoBIN();
    }
}
