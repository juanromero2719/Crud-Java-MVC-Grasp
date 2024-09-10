/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.DireccionController;
import Controlador.MunicipioController;
import Ubicacion.Direccion;
import Ubicacion.Municipio;
/**
 *
 * @author wrydmoon
 */
public class DireccionServicio {
    
    private final DireccionController direccionController;
    private final MunicipioController municipioController;

    public DireccionServicio() {
        this.direccionController = new DireccionController();
        this.municipioController = new MunicipioController();
    }
    
    public void agregarDireccion(int id, int municipioId, String calle, String carrera, String coordenada, String descripcion) {
        Municipio municipio = municipioController.consultar(municipioId);
        if (municipio != null) {
            direccionController.agregar(id, municipio, calle, carrera, coordenada, descripcion);
        } else {
            throw new IllegalArgumentException("Municipio no encontrado.");
        }
    }
    
    public Direccion consultarDireccion(int id) {
        return direccionController.consultar(id);
    }

    public void actualizarDireccion(int id, int municipioId, String calle, String carrera, String coordenada, String descripcion) {
        Municipio municipio = municipioController.consultar(municipioId);
        if (municipio != null) {
            direccionController.actualizar(id, municipio, calle, carrera, coordenada, descripcion);
        } else {
            throw new IllegalArgumentException("Municipio no encontrado.");
        }
    }

    public void eliminarDireccion(int id) {
        direccionController.eliminar(id);
    }

    public void listarDirecciones() {
        direccionController.listar();
    }

    public void guardarEnArchivoCSV(Direccion direccion) {
        direccionController.guardarEnArchivoCSV(direccion);
    }

    public void leerDesdeArchivoCSV() {
        direccionController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(Direccion direccion) {
        direccionController.guardarEnArchivoBIN(direccion);
    }

    public void leerDesdeArchivoBIN() {
        direccionController.leerDesdeArchivoBIN();
    }
    
    public String consultaDireccionCompleta(int id){
        return direccionController.obtenerDireccionCompleta(id);
    }
}
    

