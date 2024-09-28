/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Fabrica.ConexionFabrica;
import Fabrica.DepartamentoFabrica;
import Interface.IGestorDatos;
import Repositorio.H2.PaisDAOH2;
import Ubicacion.Departamento;
import Ubicacion.Pais;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class DepartamentoServicio {
    
    private final IGestorDatos<Departamento> departamentoDAO;
    private final DepartamentoFabrica departamentoFabrica;
    private final IGestorDatos<Pais> paisDAO;

    public DepartamentoServicio() {
        this.departamentoFabrica = new DepartamentoFabrica();
        this.departamentoDAO = ConexionFabrica.getGestorDatos(Departamento.class);
        this.paisDAO = ConexionFabrica.getGestorDatos(Pais.class);
    }

    public void agregarDepartamento(int idDepartamento, String nombreDepartamento, int idPais) {
        
        Pais pais = paisDAO.consultar(idPais);
              
        if (pais != null) {
            Departamento departamento = departamentoFabrica.crearDepartamento(idDepartamento, nombreDepartamento, pais);
            departamentoDAO.crear(departamento);
        }else{
            System.out.println("Pais no encontrado");
        }
        
    }

    public Departamento consultarDepartamento(int idPais) {
        
        Departamento departamento = departamentoDAO.consultar(idPais);
        
        if(departamento != null){
            System.out.println(departamento.toString());
            return departamento;
        }
        
        System.out.println("Departamento no encontrado");
        return null;
    }

    public void actualizarDepartamento(int idDepartamento, String nombreDepartamento, int idPais) {
        
        Pais pais = paisDAO.consultar(idPais);
           
        if (pais != null) {
            
            Departamento departamento = departamentoFabrica.crearDepartamento(idDepartamento, nombreDepartamento, pais);
            departamentoDAO.actualizar(departamento);
            
        }else{
            System.out.println("Pais no encontrado");
        }
        
        
    }

    public void eliminarDepartamento(int idDepartamento) {
        departamentoDAO.eliminar(idDepartamento);
    }

    public List<Departamento> listarDepartamentos() {
        return departamentoDAO.listar();
    }

 
}
