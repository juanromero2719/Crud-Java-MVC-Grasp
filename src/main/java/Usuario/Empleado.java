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
public class Empleado extends Persona implements Serializable{

    private int id;
    private Cargo cargo;
    private double salario;

    public Empleado(Cargo cargo, double salario, int id, String nombres, String apellidos, Direccion direccion) {
        super(id, nombres, apellidos, direccion);
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
    }
    
    public Empleado(int id, Cargo cargo, double salario, Persona persona) {
        super(persona.getId(), persona.getNombres(), persona.getApellidos(), persona.getDireccion());
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }
    
    public int getPersona(){
        return getId();
    }

    public Cargo getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }
    
    @Override
    public String toString() {
        return "Empleado: " + getNombres() + " " + getApellidos() + " con id: " + getId() + " con: " + cargo + ", y salario: " + salario + "";
    }


}
