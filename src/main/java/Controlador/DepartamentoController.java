/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Fabrica.DepartamentoFabrica;
import Repositorio.DepartamentoDAO;
import Ubicacion.Departamento;
import Ubicacion.Pais;

/**
 *
 * @author wrydmoon
 */
public class DepartamentoController {
    
    private final DepartamentoDAO departamentoDAO;
    private final DepartamentoFabrica departamentoFabrica;

    public DepartamentoController() {
        this.departamentoFabrica = new DepartamentoFabrica();
        this.departamentoDAO = new DepartamentoDAO();
    }

    public void agregar(int id, String nombre, Pais pais) {
        Departamento departamento = departamentoFabrica.crearDepartamento(id, nombre, pais);
        departamentoDAO.crear(departamento);
    }

    public Departamento consultar(int id) {
        return departamentoDAO.leer(id);
    }

    public void actualizar(int id, String nombre, Pais pais) {
        
        Departamento departamento = departamentoFabrica.crearDepartamento(id, nombre, pais);
        departamentoDAO.actualizar(departamento);
    }

    public void eliminar(int id) {
        departamentoDAO.eliminar(id);
    }

    public void listar() {
        departamentoDAO.listar();
    }
    
    public void guardarEnArchivoCSV(Departamento departamento) {
        departamentoDAO.guardarEnArchivoCSV(departamento);
    }

    public void leerDesdeArchivoCSV() {
        departamentoDAO.leerDesdeArchivoCSV();
    }
    
    public void guardarEnArchivoBIN(Departamento departamento) {
        departamentoDAO.guardarEnArchivoBIN(departamento);
    }

    public void leerDesdeArchivoBIN() {
        departamentoDAO.leerDesdeArchivoBIN();
    }
}
