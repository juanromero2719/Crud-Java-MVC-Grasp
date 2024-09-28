/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Fabrica.ConexionFabrica;
import Fabrica.EmpleadoFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class EmpleadoServicio {
    
    private final IGestorDatos<Empleado> empleadoDAO;
    private final EmpleadoFabrica empleadoFabrica;
    private final IGestorDatos<Cargo> cargoDAO;
    private final IGestorDatos<Persona> personaDAO;

    public EmpleadoServicio() {
        this.empleadoFabrica = new EmpleadoFabrica();
        this.empleadoDAO = ConexionFabrica.getGestorDatos(Empleado.class);
        this.cargoDAO =  ConexionFabrica.getGestorDatos(Cargo.class);
        this.personaDAO =  ConexionFabrica.getGestorDatos(Persona.class);
    }
   
    public void agregarEmpleado(int idEmpleado, int idCargo, double salario, int idPersona) {
        
        Cargo cargo = cargoDAO.consultar(idCargo);
        Persona persona = personaDAO.consultar(idPersona);
        
        if (cargo == null || persona == null) {
            System.out.println("Persona o cargo no encontrado.");           
        } else {
            Empleado empleado = empleadoFabrica.crearEmpleado(idEmpleado, cargo, salario, persona);
            empleadoDAO.crear(empleado);
        }
        
    }

    public Empleado consultarEmpleado(int idEmpleado) {
        Empleado empleado = empleadoDAO.consultar(idEmpleado);
        
        if(empleado != null){
            System.out.println(empleado.toString());
            return empleado;
        }
        
        System.out.println("Empleado no encontrado");
        return null;
    }

    public void actualizarEmpleado(int idEmpleado, int idCargo, double salario, int idPersona) {
        
        Cargo cargo = cargoDAO.consultar(idCargo);
        Persona persona = personaDAO.consultar(idPersona);
        
        if (cargo == null || persona == null) {
            System.out.println("Persona o cargo no encontrado.");
        } else {
            Empleado empleado = empleadoFabrica.crearEmpleado(idEmpleado, cargo, salario, persona);
            empleadoDAO.actualizar(empleado);           
        }
        
    }

    public void eliminarEmpleado(int idEmpleado) {
        empleadoDAO.eliminar(idEmpleado);
    }

    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listar();
    }  
}
