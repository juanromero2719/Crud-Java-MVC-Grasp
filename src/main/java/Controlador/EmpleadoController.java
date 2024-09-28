/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Servicio.EmpleadoServicio;
import Usuario.Empleado;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class EmpleadoController {
    
    private final EmpleadoServicio empleadoServicio;
    
    public EmpleadoController(){
        this.empleadoServicio = new EmpleadoServicio();
    }
    
    public void agregarEmpleado(int idEmpleado, int idCargo, double salario, int idPersona) {
        empleadoServicio.agregarEmpleado(idEmpleado, idCargo, salario, idPersona);
    }

    public Empleado consultarEmpleado(int idEmpleado) {
        return empleadoServicio.consultarEmpleado(idEmpleado);
    }

    public void actualizarEmpleado(int idEmpleado, int idCargo, double salario, int idPersona) {
        empleadoServicio.actualizarEmpleado(idEmpleado, idCargo, salario, idPersona);
    }

    public void eliminarEmpleado(int idEmpleado) {
        empleadoServicio.eliminarEmpleado(idEmpleado);
    }

    public List<Empleado> listarEmpleados() {
        return empleadoServicio.listarEmpleados();
    }
    
}
