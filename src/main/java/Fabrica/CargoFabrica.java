/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Usuario.Cargo;

/**
 *
 * @author wrydmoon
 */
public class CargoFabrica {
    
    public Cargo crearCargo(int idCargo, String nombreCargo){
        return new Cargo(idCargo, nombreCargo);
    }
}
