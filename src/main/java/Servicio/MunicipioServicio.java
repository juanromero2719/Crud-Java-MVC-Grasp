 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import Fabrica.ConexionFabrica;
import Fabrica.MunicipioFabrica;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
import Ubicacion.Municipio;
import java.util.List;
/**
 *
 * @author wrydmoon
 */
public class MunicipioServicio {
    
    private final IGestorDatos<Departamento> departamentoDAO;
    private final MunicipioFabrica municipioFabrica;
    private final IGestorDatos<Municipio> MunicipioDAO;

    public MunicipioServicio() {
        this.municipioFabrica = new MunicipioFabrica();
        this.departamentoDAO = ConexionFabrica.getGestorDatos(Departamento.class);
        this.MunicipioDAO = ConexionFabrica.getGestorDatos(Municipio.class);
    }

    public void agregarMunicipio(int idMunicipio, String nombreMunicipio, int idDepartamento) {
        
        Departamento departamento = departamentoDAO.consultar(idDepartamento);
        
        if (departamento != null) {
            Municipio municipio = municipioFabrica.crearMunicipio(idMunicipio, nombreMunicipio, departamento);
            MunicipioDAO.crear(municipio);
        }else{
            System.out.println("Departamento no encontrado");
        }
        
        
        
    }

    public Municipio consultarMunicipio(int idMunicipio) {
        
        Municipio municipio = MunicipioDAO.consultar(idMunicipio);
        
        if(municipio != null){
            System.out.println(municipio.toString());
            return municipio;
        }
        
        System.out.println("Municipio no encontrado");
        return null;
    }

    public void actualizarMunicipio(int idMunicipio, String nombreMunicipio, int idDepartamento) {
        
        Departamento departamento = departamentoDAO.consultar(idDepartamento);
        
        if (departamento != null) {
            Municipio municipio = municipioFabrica.crearMunicipio(idMunicipio, nombreMunicipio, departamento);
            MunicipioDAO.actualizar(municipio);
        } else {
            System.out.println("Departamento no encontrado");
        }
    }

    public void eliminarMunicipio(int idMunicipio) {
        MunicipioDAO.eliminar(idMunicipio);
    }

    public List<Municipio> listarMunicipios() {
        return MunicipioDAO.listar();
    }
  
}
