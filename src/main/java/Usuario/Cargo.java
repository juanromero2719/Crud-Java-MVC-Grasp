/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;
import Servicio.Todos;
import java.io.Serializable;
/**
 *
 * @author Estudiante_MCA
 */
public class Cargo implements Todos, Serializable{
    private int id;
    private String nombre;

    public Cargo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
     
    @Override
    public String informacion() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Cargo: " + nombre + ", con id: " + id + "";
    }
    
    
}
