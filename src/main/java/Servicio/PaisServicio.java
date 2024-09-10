/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.PaisController;
import Ubicacion.Pais;

/**
 *
 * @author wrydmoon
 */
public class PaisServicio {
    
    private final PaisController paisController;

    public PaisServicio() {
        this.paisController = new PaisController();
    }
    
    public void agregarPais(int id, String nombre) {
        paisController.agregarPais(id, nombre);
    }

    public Pais obtenerPais(int id) {
        return paisController.obtenerPais(id);
    }

    public void listarPaises() {
        paisController.listarPaises();
    }

    public void actualizarPais(int id, String nombre) {
        paisController.actualizarPais(id, nombre);
    }

    public void eliminarPais(int id) {
        paisController.eliminarPais(id);
    }

    public void guardarEnArchivoCSV(Pais pais) {
        paisController.guardarEnArchivoCSV(pais);
    }

    public void leerDesdeArchivoCSV() {
        paisController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(Pais pais) {
        paisController.guardarEnArchivoBIN(pais);
    }

    public void leerDesdeArchivoBIN() {
        paisController.leerDesdeArchivoBIN();
    }
    
}
