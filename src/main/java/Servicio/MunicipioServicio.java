/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.DepartamentoController;
import Controlador.MunicipioController;
import Ubicacion.Departamento;
import Ubicacion.Municipio;
/**
 *
 * @author wrydmoon
 */
public class MunicipioServicio {
    
    private final MunicipioController municipioController;
    private final DepartamentoController departamentoController;

    public MunicipioServicio() {
        this.municipioController = new MunicipioController();
        this.departamentoController = new DepartamentoController();
    }

    public void agregarMunicipio(int id, String nombre, int departamentoId) {
        Departamento departamento = departamentoController.consultar(departamentoId);
        if (departamento != null) {
            municipioController.agregar(id, nombre, departamento);
        } else {
            throw new IllegalArgumentException("Departamento no encontrado.");
        }
    }

    public Municipio consultarMunicipio(int id) {
        return municipioController.consultar(id);
    }

    public void actualizarMunicipio(int id, String nombre, int departamentoId) {
        Departamento departamento = departamentoController.consultar(departamentoId);
        if (departamento != null) {
            municipioController.actualizar(id, nombre, departamento);
        } else {
            throw new IllegalArgumentException("Departamento no encontrado.");
        }
    }

    public void eliminarMunicipio(int id) {
        municipioController.eliminar(id);
    }

    public void listarMunicipios() {
        municipioController.listar();
    }

    public void guardarEnArchivoCSV(int id) {
        Municipio municipio = municipioController.consultar(id);
        if (municipio != null) {
            municipioController.guardarEnArchivoCSV(municipio);
        } else {
            throw new IllegalArgumentException("Municipio no encontrado.");
        }
    }

    public void leerDesdeArchivoCSV() {
        municipioController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(int id) {
        Municipio municipio = municipioController.consultar(id);
        if (municipio != null) {
            municipioController.guardarEnArchivoBIN(municipio);
        } else {
            throw new IllegalArgumentException("Municipio no encontrado.");
        }
    }

    public void leerDesdeArchivoBIN() {
        municipioController.leerDesdeArchivoBIN();
    }
    
}
