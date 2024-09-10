/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.EmpleadoFabrica;
import Repositorio.EmpleadoDAO;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;

/**
 *
 * @author wrydmoon
 */
public class EmpleadoController {
    
    private final EmpleadoDAO empleadoDAO;
    private final EmpleadoFabrica empleadoFabrica;
    
    public EmpleadoController(){
        this.empleadoDAO = new EmpleadoDAO();
        this.empleadoFabrica = new EmpleadoFabrica();
    }
    
    public void agregar(int id, Cargo cargo, double salario, Persona persona) {
        Empleado empleado = empleadoFabrica.crearEmpleado(id, cargo, salario, persona);
        empleadoDAO.crear(empleado);
    }

    public Empleado consultar(int id) {
        return empleadoDAO.leer(id);
    }

    public void actualizar(int id, Cargo cargo, double salario, Persona persona) {
        Empleado empleado = empleadoFabrica.crearEmpleado(id, cargo, salario, persona);
        empleadoDAO.actualizar(empleado);
    }

    public void eliminar(int id) {
        empleadoDAO.eliminar(id);
    }

    public void listar() {
        empleadoDAO.listar();
    }
    
    public void guardarEnArchivoCSV(Empleado empleado) {
        empleadoDAO.guardarEnArchivoCSV(empleado);
    }

    public void leerDesdeArchivoCSV() {
        empleadoDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Empleado empleado) {
        empleadoDAO.guardarEnArchivoBIN(empleado);
    }

    public void leerDesdeArchivoBIN() {
        empleadoDAO.leerDesdeArchivoBIN();
    }
}
