/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Ubicacion.Departamento;
import Ubicacion.Pais;

/**
 *
 * @author wrydmoon
 */
public class DepartamentoFabrica {
    
    public Departamento crearDepartamento(int idDepartamento, String nombreDepartamento, Pais pais){
        return new Departamento(idDepartamento, nombreDepartamento, pais);
    }
}
