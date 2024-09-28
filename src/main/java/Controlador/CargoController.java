/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.CargoServicio;
import Usuario.Cargo;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class CargoController {
    
    private final CargoServicio cargoServicio;
    
    public CargoController() {
        this.cargoServicio = new CargoServicio();
    }
    
    public void agregarCargo(int idCargo, String nombreCargo) {
        cargoServicio.agregarCargo(idCargo, nombreCargo);
    }
    
    public Cargo obtenerCargo(int idCargo) {
        return cargoServicio.obtenerCargo(idCargo);
     }
     
    public List<Cargo> listarCargos() {
        return cargoServicio.listarCargos(); 
    }
    
    public void actualizarCargo(int idCargo, String nombreCargo) {      
        cargoServicio.actualizarCargo(idCargo, nombreCargo);  
    }
    
    public void eliminarCargo(int idCargo) {
        cargoServicio.eliminarCargo(idCargo);
    }
      
}
