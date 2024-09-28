/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;


import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;



/**
 *
 * @author wrydmoon
 */
public class EmpleadoFabrica {
    
    public Empleado crearEmpleado(int idEmpleado, Cargo cargo, double salario, Persona persona){
        return new Empleado(idEmpleado, cargo, salario, persona);
    }
}
