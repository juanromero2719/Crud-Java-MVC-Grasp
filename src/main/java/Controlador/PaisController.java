/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.PaisFabrica;
import Repositorio.CargoDAO;
import Repositorio.PaisDAO;
import Ubicacion.Pais;

/**
 *
 * @author wrydmoon
 */
public class PaisController {
    
    private PaisDAO paisDAO;
    private final PaisFabrica paisFabrica;
    
    public PaisController() {
        this.paisDAO =  new PaisDAO();
        this.paisFabrica = new PaisFabrica();
    }
    
    public void agregarPais(int id, String nombre) {
        Pais pais = paisFabrica.crearPais(id, nombre);
        paisDAO.crear(pais);
    }
    
    public Pais obtenerPais(int id) {
        return paisDAO.leer(id);
    }
     
    public void listarPaises() {
        paisDAO.listar();
    }
    
    public void actualizarPais(int id, String nombre) {
        
        Pais pais = paisFabrica.crearPais(id, nombre);
        paisDAO.actualizar(pais);
    }
    
    public void eliminarPais(int id) {
        paisDAO.eliminar(id);
    }
    
    public void guardarEnArchivoCSV(Pais pais) {
        paisDAO.guardarEnArchivoCSV(pais);
    }

    public void leerDesdeArchivoCSV() {
        paisDAO.leerDesdeArchivoCSV();
    }
    
     public void guardarEnArchivoBIN(Pais pais) {
        paisDAO.guardarEnArchivoBIN(pais);
    }

    public void leerDesdeArchivoBIN() {
        paisDAO.leerDesdeArchivoBIN();
    }
    
}
