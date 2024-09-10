/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.DireccionController;
import Controlador.PersonaController;
import Ubicacion.Direccion;
import Usuario.Persona;
/**
 *
 * @author wrydmoon
 */
public class PersonaServicio {
    
    private final PersonaController personaController;
    private final DireccionController direccionController;

    public PersonaServicio() {
        this.personaController = new PersonaController();
        this.direccionController = new DireccionController();
    }

    public void agregarPersona(int id, String nombres, String apellidos, int direccionId) {
        
        Direccion direccion = direccionController.consultar(direccionId);
        
        if (direccion == null) {
            System.out.println("Dirección no encontrada. No se puede agregar la persona.");
            return;
        }
          
        personaController.agregar(id, nombres, apellidos, direccion);
    }

    public Persona consultarPersona(int id) {
        return personaController.consultar(id);
    }

    public void actualizarPersona(int id, String nombres, String apellidos, int idDireccion) {
        
        Direccion direccion = direccionController.consultar(idDireccion);
        if (direccion == null) {
            System.out.println("Dirección no encontrada. No se puede actualizar la persona.");
            return;
        }
        personaController.actualizar(id, nombres, apellidos, direccion);
        
    }

    public void eliminarPersona(int id) {
        personaController.eliminar(id);
    }

    public void listarPersonas() {
        personaController.listar();
    }

    public void guardarEnArchivoCSV(Persona persona) {
        personaController.guardarEnArchivoCSV(persona);
    }

    public void leerDesdeArchivoCSV() {
        personaController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(Persona persona) {
        personaController.guardarEnArchivoBIN(persona);
 
    }

    public void leerDesdeArchivoBIN() {
        personaController.leerDesdeArchivoBIN();
    }
}
