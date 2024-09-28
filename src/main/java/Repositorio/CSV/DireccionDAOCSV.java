/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.DireccionFabrica;
import Interface.IGestorDatos;
import Ubicacion.Direccion;
import Ubicacion.Municipio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class DireccionDAOCSV implements IGestorDatos<Direccion>{
    
    private final static String ARCHIVO_CSV = "data/direccion.csv";
    private final DireccionFabrica direccionFabrica;
    private final IGestorDatos<Municipio> municipioDAO;
    
    public DireccionDAOCSV(){
        this.direccionFabrica = new DireccionFabrica();
        this.municipioDAO = ConexionFabrica.getGestorDatos(Municipio.class);
    }

    @Override
    public void crear(Direccion direccion) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim()); 
                    if (idExistente == direccion.getId()) {
                        idExiste = true;
                        break;  
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }

        if (!idExiste) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
                writer.write(direccion.getId() + ", " + direccion.getCalle() + ", " + direccion.getCarrera() + ", " + direccion.getCoordenada() + ", " + direccion.getDescripcion() + ", " + direccion.getMunicipio().getId());
                writer.newLine();
                System.out.println("Direccion agregada con éxito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + direccion.getId() + " ya existe. No se puede agregar la direccion.");
        }
    }

    @Override
    public Direccion consultar(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", "); 
                if (partes.length < 6) {
                    System.err.println("Formato incorrecto en la línea: " + linea);
                    continue;
                }

                int direccionId = Integer.parseInt(partes[0].trim());
                String calle = partes[1].trim();
                String carrera = partes[2].trim();
                String coordenada = partes[3].trim();
                String descripcion = partes[4].trim();
                int municipioId = Integer.parseInt(partes[5].trim());

                if (direccionId == id) {
                    Municipio municipio = municipioDAO.consultar(municipioId);
                    return  direccionFabrica.crearDireccion(direccionId, municipio, calle, carrera, coordenada, descripcion);
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer el archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear el ID o el municipio: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Direccion> listar() {
        List<Direccion> direcciones = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 6) { 
                    int id = Integer.parseInt(datos[0].trim());
                    String calle = datos[1].trim();
                    String carrera = datos[2].trim();
                    String coordenada = datos[3].trim();
                    String descripcion = datos[4].trim();
                    int municipioId = Integer.parseInt(datos[5].trim());

                    Municipio municipio = municipioDAO.consultar(municipioId);
                    direcciones.add(direccionFabrica.crearDireccion(id, municipio, calle, carrera, coordenada, descripcion));
                    
                } else {
                    System.err.println("Formato de linea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leídos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Direccion direccion : direcciones) {
            System.out.println(direccion);
        }

        return direcciones;
    }


    @Override
    public void actualizar(Direccion direccion) {
        System.out.println("Metodo no soportado para archivos planos");
    }

    @Override
    public void eliminar(int id) {
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim());
                    if (idExistente != id) {
                        lineas.add(linea); 
                    } else {
                        encontrado = true;  
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException excepcion) {
            System.err.println("Error al escribir en archivo CSV: " + excepcion.getMessage());
        }

        if (encontrado) {
            System.out.println("La direccion con ID " + id + " ha sido eliminada con exito.");
        } else {
            System.out.println("No se encontro ninguna direccion con el ID " + id + ".");
        }
    }

        
        
}
