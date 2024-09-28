/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Servicio.MunicipioServicio;
import Ubicacion.Municipio;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class MunicipioController {
    
    private final MunicipioServicio municipioServicio;

    public MunicipioController() {
        this.municipioServicio = new MunicipioServicio();
    }
    
    public void agregarMunicipio(int idMunicipio, String nombre, int idDepartamento) {       
        municipioServicio.agregarMunicipio(idMunicipio, nombre, idDepartamento);
    }

    public Municipio consultarMunicipio(int idMunicipio) {
        return municipioServicio.consultarMunicipio(idMunicipio);
    }

    public void actualizarMunicipio(int idMunicipio, String nombre, int idDepartamento) {
        municipioServicio.actualizarMunicipio(idMunicipio, nombre, idDepartamento);
    }

    public void eliminarMunicipio(int idMunicipio) {
        municipioServicio.eliminarMunicipio(idMunicipio);
    }

    public List<Municipio> listarMunicipios() {
        return municipioServicio.listarMunicipios();
    }
       
}
