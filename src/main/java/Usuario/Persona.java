/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import Ubicacion.Direccion;
import Servicio.Todos;
import java.io.Serializable;

/**
 *
 * @author Estudiante_MCA
 */
public class Persona implements Todos, Serializable {

    private int id;
    private String nombres;
    private String apellidos;
    private Direccion direccion;

    public Persona(int id, String nombres, String apellidos, Direccion direccion) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String informacion() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Persona con id:" + id + " de nombre " + nombres + " " + apellidos + ", " + direccion + '}';
    }

}
