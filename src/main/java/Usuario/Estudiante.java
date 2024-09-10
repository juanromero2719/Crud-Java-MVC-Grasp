/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import Ubicacion.Direccion;
import java.io.Serializable;

/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante extends Persona implements Serializable{
    
    private int id;
    private String codigo;
    private String programa;
    private double promedio;
    
    public Estudiante(int id, String nombres, String apellidos, Direccion direccion, String codigo, String programa, double promedio) {
        super(id, nombres, apellidos, direccion);
        this.codigo = codigo;
        this.programa = programa;
        this.promedio = promedio;
    }
    
    public Estudiante(int id, String codigo, String programa, double promedio, Persona persona) {
        super(persona.getId(), persona.getNombres(), persona.getApellidos(), persona.getDireccion());
        this.id = id;
        this.codigo = codigo;
        this.programa = programa;
        this.promedio = promedio;
    }
    
    
    public int getPersona(){
        return getId();
    }

    public int getId() {
        return id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getPrograma() {
        return programa;
    }

    public double getPromedio() {
        return promedio;
    }


    @Override
    public String toString() {
        return "Estudiante: " + getNombres() + " " + getApellidos() + " con id: " + getId() + " con codigo: " + codigo + ", del programa: " + programa + " con promedio de : " + promedio + "";
    }
    
    
    
}
