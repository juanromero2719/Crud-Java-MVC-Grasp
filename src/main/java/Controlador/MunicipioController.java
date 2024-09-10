/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.MunicipioFabrica;
import Repositorio.MunicipioDAO;
import Ubicacion.Departamento;
import Ubicacion.Municipio;

/**
 *
 * @author ASUS
 */
public class MunicipioController {
    
    private final MunicipioDAO municipioDAO;
    private final MunicipioFabrica municipioFabrica;

    public MunicipioController() {
        this.municipioFabrica = new MunicipioFabrica();
        this.municipioDAO = new MunicipioDAO();
    }
    
    public void agregar(int id, String nombre, Departamento departamento) {
        Municipio municipio = municipioFabrica.crearMunicipio(id, nombre, departamento);
        municipioDAO.crear(municipio);
    }

    public Municipio consultar(int id) {
        return municipioDAO.leer(id);
    }

    public void actualizar(int id, String nombre, Departamento departamento) {
        Municipio municipio = municipioFabrica.crearMunicipio(id, nombre, departamento);
        municipioDAO.actualizar(municipio);
    }

    public void eliminar(int id) {
        municipioDAO.eliminar(id);
    }

    public void listar() {
        municipioDAO.listar();
    }
    
    public void guardarEnArchivoCSV(Municipio municipio) {
        municipioDAO.guardarEnArchivoCSV(municipio);
    }

    public void leerDesdeArchivoCSV() {
        municipioDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Municipio municipio) {
        municipioDAO.guardarEnArchivoBIN(municipio);
    }

    public void leerDesdeArchivoBIN() {
        municipioDAO.leerDesdeArchivoBIN();
    }
    
}
