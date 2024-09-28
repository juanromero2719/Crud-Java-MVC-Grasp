/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;


import Fabrica.ConexionFabrica;
import Fabrica.PersonaFabrica;
import Interface.IGestorDatos;
import Ubicacion.Direccion;
import Usuario.Persona;
import java.util.List;
/**
 *
 * @author wrydmoon
 */
public class PersonaServicio {
    
    private final PersonaFabrica personaFabrica;
    private final IGestorDatos<Direccion> direccionDAO;
    private final IGestorDatos<Persona> personaDAO;

    public PersonaServicio() {
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
        this.personaFabrica = new PersonaFabrica();
        this.direccionDAO =  ConexionFabrica.getGestorDatos(Direccion.class);
    }

    public void agregarPersona(int idPersona, String nombres, String apellidos, int idDireccion) {
        
        Direccion direccion = direccionDAO.consultar(idDireccion);
        
        if(direccion!=null){                     
        Persona persona = personaFabrica.crearPersona(idPersona, nombres, apellidos, direccion);
        personaDAO.crear(persona);
        }else{
            System.out.println("Direccion no encontrada");
        }
              
    }

    public Persona consultarPersona(int idPersona) {
        Persona persona = personaDAO.consultar(idPersona);
        
        if(persona != null){
            System.out.println(persona.toString());
            return persona;
        }
        
        System.out.println("Persona no encontrada");
        return null;
    }

    public void actualizarPersona(int idPersona, String nombres, String apellidos, int idDireccion) {
        
        Direccion direccion = direccionDAO.consultar(idDireccion);
        
        if (direccion != null) {           
            Persona persona = personaFabrica.crearPersona(idPersona, nombres, apellidos, direccion);         
            personaDAO.actualizar(persona);  
        }else{
            System.out.println("Direccion no encontrada. No se puede actualizar la persona.");
        }
                                
    }

    public void eliminarPersona(int idPersona) {
        personaDAO.eliminar(idPersona);
    }

    public List<Persona> listarPersonas() {
        return personaDAO.listar();
    }    
}
