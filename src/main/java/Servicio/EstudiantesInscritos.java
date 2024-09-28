/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Usuario.Estudiante;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Estudiante_MCA
 */
public class EstudiantesInscritos {
    
    private List<Estudiante> listado;

    public EstudiantesInscritos() { 
         this.listado = new ArrayList<Estudiante>();
    }

    public void imprimirListado() {
        System.out.println("Listado de Estudiantes Inscritos:");
        for (Estudiante estudiante : listado) {
            System.out.println(estudiante.toString());
        }
    }
    
    public void adicionar(Estudiante estudiante) {
        if (estudiante != null) {
            listado.add(estudiante);
            System.out.println("Estudiante añadido: " + estudiante.toString());
        } else {
            System.out.println("El estudiante no puede ser nulo.");
        }
    }
}
