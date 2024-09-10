/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.CargoController;
import Usuario.Cargo;

/**
 *
 * @author wrydmoon
 */
public class CargoServicio {
    
    private final CargoController cargoController;

    public CargoServicio() {
        this.cargoController = new CargoController();
    }

    public void agregarCargo(int id, String nombre) {
        cargoController.agregarCargo(id, nombre);
    }

    public Cargo obtenerCargo(int id) {
        return cargoController.obtenerCargo(id);
    }

    public void listarCargos() {
        cargoController.listarCargos();
    }

    public void actualizarCargo(int id, String nombre) {
        cargoController.actualizarCargo(id, nombre);
    }

    public void eliminarCargo(int id) {
        cargoController.eliminarCargo(id);
    }

    public void guardarEnArchivoCSV(Cargo cargo) {
        cargoController.guardarEnArchivoCSV(cargo);
    }

    public void leerDesdeArchivoCSV() {
        cargoController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(Cargo cargo) {
        cargoController.guardarEnArchivoBIN(cargo);
    }

    public void leerDesdeArchivoBIN() {
        cargoController.leerDesdeArchivoBIN();
    }
}
