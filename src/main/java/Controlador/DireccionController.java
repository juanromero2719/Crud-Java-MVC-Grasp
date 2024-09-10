/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.DireccionFabrica;
import Repositorio.DireccionDAO;
import Ubicacion.Direccion;
import Ubicacion.Municipio;

/**
 *
 * @author ASUS
 */
public class DireccionController {
    
    private final DireccionDAO direccionDAO;
    private final DireccionFabrica direccionesFabrica;
    
    public DireccionController(){
        this.direccionDAO = new DireccionDAO();
        this.direccionesFabrica = new DireccionFabrica();
    }
    
    public void agregar(int id, Municipio municipio, String calle, String carrera, String coordenada, String descripcion) {
        Direccion direccion = direccionesFabrica.crearDireccion(id, municipio, calle, carrera, coordenada, descripcion);
        direccionDAO.crear(direccion);
    }

    public Direccion consultar(int id) {
        return direccionDAO.leer(id);
    }

    public void actualizar(int id, Municipio municipio, String calle, String carrera, String coordenada, String descripcion) {
        Direccion direccion = direccionesFabrica.crearDireccion(id, municipio, calle, carrera, coordenada, descripcion);
        direccionDAO.actualizar(direccion);
    }

    public void eliminar(int id) {
        direccionDAO.eliminar(id);
    }

    public void listar() {
        direccionDAO.listar();
    }
    
     public void guardarEnArchivoCSV(Direccion direccion) {
        direccionDAO.guardarEnArchivoCSV(direccion);
    }

    public void leerDesdeArchivoCSV() {
        direccionDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Direccion direccion) {
        direccionDAO.guardarEnArchivoBIN(direccion);
    }

    public void leerDesdeArchivoBIN() {
        direccionDAO.leerDesdeArchivoBIN();
    }
    
    public String obtenerDireccionCompleta(int id) {
        Direccion direccion = direccionDAO.leer(id);
        if (direccion != null) {
            return direccion.obtenerDireccionCompleta();
        } else {
            return "Direccion no encontrada.";
        }
    }
}
