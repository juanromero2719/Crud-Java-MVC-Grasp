/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.DepartamentoServicio;
import Ubicacion.Departamento;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class DepartamentoController {
    
    private final DepartamentoServicio departamentoServicio;
          

    public DepartamentoController() {
        this.departamentoServicio = new DepartamentoServicio();
    }

    public void agregarDepartamento(int idDepartamento, String nombreDepartamento, int idPais) {
        departamentoServicio.agregarDepartamento(idDepartamento, nombreDepartamento, idPais);
    }

    public Departamento consultarDepartamento(int idDepartamento) {       
        return departamentoServicio.consultarDepartamento(idDepartamento);
    }

    public void actualizarDepartamento(int departamentoId, String nombreDepartamento, int idPais) {     
        departamentoServicio.actualizarDepartamento(departamentoId, nombreDepartamento, idPais);     
    }

    public void eliminarDepartamento(int idDepartamento) {
        departamentoServicio.eliminarDepartamento(idDepartamento);
    }

    public List<Departamento> listarDepartamentos() {
        return departamentoServicio.listarDepartamentos();
    }
    
}
