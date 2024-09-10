/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Departamento;
import Ubicacion.Municipio;

/**
 *
 * @author wrydmoon
 */
public class MunicipioFabrica {
    
    public Municipio crearMunicipio(int id, String nombre, Departamento departamento){
        return new Municipio(nombre, id, departamento);
    }
    
}
