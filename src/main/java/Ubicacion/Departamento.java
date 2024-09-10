/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ubicacion;

import java.io.Serializable;

/**
 *
 * @author Estudiante_MCA
 */

public class Departamento implements Serializable {

    private int id;
    private String nombre;
    private Pais pais;

    public Departamento(int id, String nombre, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;      
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pais getPais() {
        return pais;
    }
  
    @Override
    public String toString() {
        return "Departamento ID: " + id + ", Nombre: " + nombre + ", " + pais;
    }

}
