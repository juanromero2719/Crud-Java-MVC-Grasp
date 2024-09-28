/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.PaisServicio;
import Ubicacion.Pais;
import java.util.List;


/**
 *
 * @author wrydmoon
 */
public class PaisController {
    
    
    private final PaisServicio paisServicio;
    
    public PaisController() {
        this.paisServicio = new PaisServicio();
    }
    
    public void agregarPais(int idPais, String nombrePais) {
        paisServicio.agregarPais(idPais, nombrePais);     
    }
    
    public Pais obtenerPais(int idPais) {
        return paisServicio.obtenerPais(idPais);
    }
     
    public List<Pais> listarPaises() {
        return paisServicio.listarPaises();
    }
    
    public void actualizarPais(int idPais, String nombrePais) {
        paisServicio.actualizarPais(idPais, nombrePais);

    }
    
    public void eliminarPais(int idPais) {
        paisServicio.eliminarPais(idPais);
    }
       
}
