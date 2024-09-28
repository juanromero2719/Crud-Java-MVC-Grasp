/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;



import Fabrica.CargoFabrica;
import Fabrica.ConexionFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class CargoServicio {
    
    private final CargoFabrica cargoFabrica;
    private final IGestorDatos<Cargo> cargoDAO;
    
    public CargoServicio() {
        this.cargoDAO = ConexionFabrica.getGestorDatos(Cargo.class);
        this.cargoFabrica = new CargoFabrica();
        
    }

    public void agregarCargo(int idCargo, String nombreCargo) {
        
        Cargo cargo = cargoFabrica.crearCargo(idCargo, nombreCargo);
        cargoDAO.crear(cargo);
        
    }

    public Cargo obtenerCargo(int idCargo) {
        
        Cargo cargo = cargoDAO.consultar(idCargo);
        
        if(cargo != null ){
            System.out.println(cargo.toString());
            return cargo;
        }
        
        System.out.println("Cargo no encontrado");
        return null;
               
    }

    public List<Cargo> listarCargos() {
        return cargoDAO.listar();      
    }

    public void actualizarCargo(int idCargo, String nombreCargo) {       
        Cargo cargo = cargoFabrica.crearCargo(idCargo, nombreCargo);
        cargoDAO.actualizar(cargo);
    }

    public void eliminarCargo(int idCargo) {
        cargoDAO.eliminar(idCargo);
    }

}
