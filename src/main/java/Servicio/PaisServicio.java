/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;


import Fabrica.ConexionFabrica;
import Fabrica.PaisFabrica;
import Interface.IGestorDatos;
import Ubicacion.Pais;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class PaisServicio {
    
    private IGestorDatos<Pais> paisDAO;
    private final PaisFabrica paisFabrica;

    public PaisServicio() {
        this.paisDAO =  ConexionFabrica.getGestorDatos(Pais.class);
        this.paisFabrica = new PaisFabrica();
    }
    
    public void agregarPais(int idPais, String nombrePais) {
        Pais pais = paisFabrica.crearPais(idPais, nombrePais);
        paisDAO.crear(pais);
    }

    public Pais obtenerPais(int idPais) {
        
        Pais pais = paisDAO.consultar(idPais);
        
        if(pais != null){
            System.out.println(pais.toString());
            return pais;
        }
        
        System.out.println("No se encontro el pais");
        return null;
    }

    public List<Pais> listarPaises() {
        return paisDAO.listar();
    }

    public void actualizarPais(int idPais, String nombrePais) {
        Pais pais = paisFabrica.crearPais(idPais, nombrePais);
        paisDAO.actualizar(pais);
    }

    public void eliminarPais(int idPais) {
        paisDAO.eliminar(idPais);
    }
  
}
