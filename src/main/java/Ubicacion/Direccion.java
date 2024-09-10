/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ubicacion;

import java.io.Serializable;

/**
 *
 * @author Estudiante_MCA
 */
public class Direccion implements Serializable{
    private int id;
    private Municipio municipio;
    private String calle;
    private String carrera;
    private String coordenada;
    private String descripcion;

    public Direccion(int id, Municipio municipio, String calle, String carrera, String coordenada, String descripcion) {
        this.id = id;
        this.municipio = municipio;
        this.calle = calle;
        this.carrera = carrera;
        this.coordenada = coordenada;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public String getCalle() {
        return calle;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public String obtenerDireccionCompleta() {
        return "Calle: " + calle + ", Carrera: " + carrera + ", Coordenada: " + coordenada + 
               ", Descripción: " + descripcion + ", Municipio: " + municipio.obtenerNombreCompleto();
    }
    
    @Override
    public String toString() {
        return "Direccion: id: " + id +
               ", calle: " + calle +
               ", carrera: " + carrera +
               ", coordenada: " + coordenada +
               ", descripcion: " + descripcion +
               ", : " + municipio;

    }
    
    
    
}
