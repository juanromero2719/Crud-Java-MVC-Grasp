/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Pais;

/**
 *
 * @author wrydmoon
 */
public class PaisFabrica {
    
    public Pais crearPais(int idPais, String nombrePais){
        return new Pais(idPais, nombrePais);
    }
   
}
