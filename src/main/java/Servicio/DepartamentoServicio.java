/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Controlador.DepartamentoController;
import Controlador.PaisController;
import Ubicacion.Departamento;
import Ubicacion.Pais;
/**
 *
 * @author wrydmoon
 */
public class DepartamentoServicio {
    
    private final DepartamentoController departamentoController;
    private final PaisController paisController;

    public DepartamentoServicio() {
        this.departamentoController = new DepartamentoController();
        this.paisController = new PaisController();
    }

    public void agregarDepartamento(int id, String nombre, int paisId) {
        Pais pais = paisController.obtenerPais(paisId);
        if (pais != null) {
            departamentoController.agregar(id, nombre, pais);
        } else {
            throw new IllegalArgumentException("País no encontrado.");
        }
    }

    public Departamento consultarDepartamento(int id) {
        return departamentoController.consultar(id);
    }

    public void actualizarDepartamento(int id, String nombre, int paisId) {
        Pais pais = paisController.obtenerPais(paisId);
        if (pais != null) {
            departamentoController.actualizar(id, nombre, pais);
        } else {
            throw new IllegalArgumentException("País no encontrado.");
        }
    }

    public void eliminarDepartamento(int id) {
        departamentoController.eliminar(id);
    }

    public void listarDepartamentos() {
        departamentoController.listar();
    }

    public void guardarEnArchivoCSV(Departamento departamento) {
        departamentoController.guardarEnArchivoCSV(departamento);
    }

    public void leerDesdeArchivoCSV() {
        departamentoController.leerDesdeArchivoCSV();
    }

    public void guardarEnArchivoBIN(Departamento departamento) {
        departamentoController.guardarEnArchivoBIN(departamento);
    }

    public void leerDesdeArchivoBIN() {
        departamentoController.leerDesdeArchivoBIN();
    }
}
