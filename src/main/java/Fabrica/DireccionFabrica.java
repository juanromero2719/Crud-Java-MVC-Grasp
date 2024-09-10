/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Direccion;
import Ubicacion.Municipio;

/**
 *
 * @author wrydmoon
 */
public class DireccionFabrica {
    
    public Direccion crearDireccion(int id, Municipio municipio, String calle, String carrera, String coordenada, String descripcion){
        return new Direccion(id, municipio, calle, carrera, coordenada, descripcion);
    }
}
