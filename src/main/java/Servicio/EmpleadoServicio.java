/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.CargoController;
import Controlador.EmpleadoController;
import Controlador.PersonaController;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class EmpleadoServicio {
    
    private final EmpleadoController empleadoController;
    private final CargoController cargoController;
    private final PersonaController personaController;

    public EmpleadoServicio() {
        this.empleadoController = new EmpleadoController();
        this.cargoController = new CargoController();
        this.personaController = new PersonaController();
    }
   
    public void agregarEmpleado(int id, int idCargo, double salario, int idPersona) {
        
        Cargo cargo = cargoController.obtenerCargo(idCargo);
        Persona persona = personaController.consultar(idPersona);
        
        if (cargo != null || persona != null) {
            empleadoController.agregar(id, cargo, salario, persona);
        } else {
            System.out.println("Persona o cargo no encontrado.");
        }
        
    }

    public Empleado consultarEmpleado(int id) {
        return empleadoController.consultar(id);
    }

    public void actualizarEmpleado(int id, int idCargo, double salario, int idPersona) {
        
        Cargo cargo = cargoController.obtenerCargo(idCargo);
        Persona persona = personaController.consultar(idPersona);
        
        if (cargo != null || persona != null) {
            empleadoController.actualizar(id, cargo, salario, persona);
        } else {
            System.out.println("Persona o cargo no encontrado.");
        }
        
    }

    public void eliminarEmpleado(int id) {
        empleadoController.eliminar(id);
    }

    public void listarEmpleados() {
        empleadoController.listar();
    }

    public void guardarEnArchivoCSV(int id) {
        Empleado empleado = empleadoController.consultar(id);
        if (empleado != null) {
            empleadoController.guardarEnArchivoCSV(empleado);
        } else {
            throw new IllegalArgumentException("Empleado no encontrado.");
        }
    }

    public void leerDesdeArchivoCSV() {
        empleadoController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(int id) {
        Empleado empleado = empleadoController.consultar(id);
        if (empleado != null) {
            empleadoController.guardarEnArchivoBIN(empleado);
        } else {
            throw new IllegalArgumentException("Empleado no encontrado.");
        }
    }

    public void leerDesdeArchivoBIN() {
        empleadoController.leerDesdeArchivoBIN();
    }
}
