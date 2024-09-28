/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;
import Usuario.Persona;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Estudiante_MCA
 */
public class PersonalInscrito {
    
   private List<Persona> listado;

    public PersonalInscrito(){
        this.listado = new ArrayList<Persona>();
    }
   
    public void imprimirListado() {
        System.out.println("Listado de Estudiantes Inscritos:");
        for (Persona persona : listado) {
            System.out.println(persona.toString());
        }
    }
    
    public void adicionar(Persona persona) {
        if (persona != null) {
            listado.add(persona);
            System.out.println("Estudiante añadido: " + persona.toString());
        } else {
            System.out.println("El estudiante no puede ser nulo.");
        }
    }
}
