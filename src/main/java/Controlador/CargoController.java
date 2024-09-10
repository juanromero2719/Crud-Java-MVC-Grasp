/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.CargoFabrica;
import Repositorio.CargoDAO;
import Ubicacion.Pais;
import Usuario.Cargo;

/**
 *
 * @author wrydmoon
 */
public class CargoController {
    
    private CargoDAO cargoDAO;
    private final CargoFabrica cargoFabrica;
    
    public CargoController() {
        this.cargoDAO = new CargoDAO();
        this.cargoFabrica = new CargoFabrica();
    }
    
    public void agregarCargo(int id, String nombre) {
        Cargo cargo = cargoFabrica.crearCargo(id, nombre);
        cargoDAO.crear(cargo);
    }
    
    public Cargo obtenerCargo(int id) {
        return cargoDAO.leer(id);
    }
     
    public void listarCargos() {
        cargoDAO.listar();
    }
    
    public void actualizarCargo(int id, String nombre) {
        Cargo cargo = cargoFabrica.crearCargo(id, nombre);
        cargoDAO.actualizar(cargo);
    }
    
    public void eliminarCargo(int id) {
        cargoDAO.eliminar(id);
    }
    
    public void guardarEnArchivoCSV(Cargo cargo) {
        cargoDAO.guardarEnArchivoCSV(cargo);
    }

    public void leerDesdeArchivoCSV() {
        cargoDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Cargo cargo) {
        cargoDAO.guardarEnArchivoBIN(cargo);
    }

    public void leerDesdeArchivoBIN() {
        cargoDAO.leerDesdeArchivoBIN();
    }
    
}
