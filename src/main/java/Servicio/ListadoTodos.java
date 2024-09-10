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
public class ListadoTodos {
    
    private List<Todos> todos;

    public ListadoTodos() {
        this.todos = new ArrayList<Todos>();
    }
    
    public int cantidad(){
    int cantidad_usuarios;
    cantidad_usuarios = todos.size();
    return cantidad_usuarios;
    }
    
    public void adicionar(Todos item) {
        if (item != null) {
            todos.add(item);
        } else {
            System.out.println("El objeto no puede ser nulo.");
        }
    }
    
    public Todos consultar(int posicion) {
        if (posicion >= 0 && posicion < todos.size()) {
            System.out.println(todos.get(posicion));
            return todos.get(posicion);
            
        } else {
            System.out.println("Posición fuera de rango.");
            return null;
        }
    }
    
    public String mostrar(int posicion) {
        Todos objeto = consultar(posicion);
        if (objeto != null) {
            return objeto.informacion();
        } else {
            System.out.println("No se pudo obtener la información del objeto en la posición " + posicion);
            return null;
        }
        
    }
    
    
}
