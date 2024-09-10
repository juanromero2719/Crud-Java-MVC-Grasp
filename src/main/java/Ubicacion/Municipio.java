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
public class Municipio implements Serializable {
    
    private int id;
    private String nombre;
    private Departamento departamento;

    public Municipio(String nombre, int id, Departamento departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
    
    public String obtenerNombreCompleto() {
        return nombre + ", " + departamento.getNombre();
    }
  
    @Override
    public String toString() {
        return "municipio: " + nombre + " con id " + id + ", " + departamento +"" ;
    }

}