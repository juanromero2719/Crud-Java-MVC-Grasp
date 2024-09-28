/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;


import Fabrica.ConexionFabrica;
import Fabrica.DireccionFabrica;
import Interface.IGestorDatos;
import Ubicacion.Direccion;
import Ubicacion.Municipio;
import java.util.List;
/**
 *
 * @author wrydmoon
 */
public class DireccionServicio {
    
    private final IGestorDatos<Direccion> direccionDAO;
    private final IGestorDatos<Municipio> municipioDAO;
    private final DireccionFabrica direccionFabrica;

    public DireccionServicio() {
        this.direccionDAO = ConexionFabrica.getGestorDatos(Direccion.class);
        this.municipioDAO = ConexionFabrica.getGestorDatos(Municipio.class);
        this.direccionFabrica = new DireccionFabrica();
    }
    
    public void agregarDireccion(int idDireccion, int idMunicipio, String calle, String carrera, String coordenada, String descripcion) {
        
        Municipio municipio = municipioDAO.consultar(idMunicipio);
        
        if (municipio != null) {
            Direccion direccion = direccionFabrica.crearDireccion(idDireccion, municipio, calle, carrera, coordenada, descripcion);
            direccionDAO.crear(direccion);
        } else {
            System.out.println("Municipio no encontrado");
        }
    }
    
    public Direccion consultarDireccion(int idDireccion) {
        
        Direccion direccion = direccionDAO.consultar(idDireccion);
        if(direccion != null){
            System.out.println(direccion.toString());
            return direccion;
        }
        
        System.out.println("Direccion no encontrada");
        return null;
        
    }

    public void actualizarDireccion(int idDireccion, int idMunicipio, String calle, String carrera, String coordenada, String descripcion) {
        
        Municipio municipio = municipioDAO.consultar(idMunicipio);
        if (municipio != null) {
            Direccion direccion = direccionFabrica.crearDireccion(idDireccion, municipio, calle, carrera, coordenada, descripcion);
            direccionDAO.actualizar(direccion);
        } else {
            System.out.println("Municipio no encontrado");
        }
    }

    public void eliminarDireccion(int idDireccion) {
        direccionDAO.eliminar(idDireccion);
    }

    public List<Direccion> listarDirecciones() {
        return direccionDAO.listar();
    }
    
    public String consultaDireccionCompleta(int idDireccion){
        
        Direccion direccion = direccionDAO.consultar(idDireccion);
        
        if (direccion != null) {
            return direccion.obtenerDireccionCompleta();
        } else {
           return "Direccion no encontrada.";
        }
    }
 
}
    

