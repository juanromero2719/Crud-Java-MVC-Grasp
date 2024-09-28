/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.DireccionServicio;
import Ubicacion.Direccion;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class DireccionController {
    
    private final DireccionServicio direccionServicio;
    
    public DireccionController(){
        this.direccionServicio = new DireccionServicio();
    }
    
    public void agregarDireccion(int idDireccion, int idMunicipio, String calle, String carrera, String coordenada, String descripcion) {
        direccionServicio.agregarDireccion(idDireccion, idMunicipio, calle, carrera, coordenada, descripcion);
    }

    public Direccion consultarDireccion(int idDireccion) {
        return direccionServicio.consultarDireccion(idDireccion);
    }

    public void actualizarDireccion(int idDireccion, int idMunicipio, String calle, String carrera, String coordenada, String descripcion) {
        direccionServicio.actualizarDireccion(idDireccion, idMunicipio, calle, carrera, coordenada, descripcion);
    }

    public void eliminarDireccion(int idDireccion) {
        direccionServicio.eliminarDireccion(idDireccion);
    }

    public List<Direccion> listarDirecciones() {
        return direccionServicio.listarDirecciones();
    }
      
    public String obtenerDireccionCompleta(int idDireccion) {
        return direccionServicio.consultaDireccionCompleta(idDireccion);
    }
}
