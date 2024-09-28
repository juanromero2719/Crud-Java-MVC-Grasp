/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.MunicipioFabrica;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
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
public class MunicipioDAOCSV implements IGestorDatos<Municipio>{
    
    private static String ARCHIVO_CSV = "data/municipio.csv";
    private final MunicipioFabrica municipioFabrica;
    private final IGestorDatos<Departamento> departamentoDAO;
    
    public MunicipioDAOCSV(){
        this.departamentoDAO = ConexionFabrica.getGestorDatos(Departamento.class);
        this.municipioFabrica = new MunicipioFabrica();
    }

    @Override
    public void crear(Municipio municipio) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", ");  
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim()); 
                    if (idExistente == municipio.getId()) {
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
                writer.write(municipio.getId() + ", " + municipio.getNombre() + ", " + municipio.getDepartamento().getId());
                writer.newLine();
                System.out.println("Municipio agregado con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + municipio.getId() + " ya existe. No se puede agregar el municipio.");
        }
    }

    @Override
    public Municipio consultar(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                if (partes.length < 3) {
                    System.err.println("Formato incorrecto en la línea: " + linea);
                    continue; 
                }

                int municipioId = Integer.parseInt(partes[0].trim());
                String nombreMunicipio = partes[1].trim();
                int departamentoId = Integer.parseInt(partes[2].trim());

                if (municipioId == id) {
                    Departamento departamento = departamentoDAO.consultar(departamentoId);
                    return municipioFabrica.crearMunicipio(municipioId, nombreMunicipio, departamento);
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer el archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear el ID: " + e.getMessage());
        }

        return null; 
    }

    @Override
    public List<Municipio> listar() {
        List<Municipio> municipios = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 3) { 
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    int departamentoId = Integer.parseInt(datos[2].trim());

                    Departamento departamento = departamentoDAO.consultar(departamentoId);
                    municipios.add(municipioFabrica.crearMunicipio(id, nombre, departamento));
                    
                } else {
                    System.err.println("Formato de línea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leídos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Municipio municipio : municipios) {
            System.out.println(municipio);
        }

        return municipios;
    }

    @Override
    public void actualizar(Municipio municipio) {
        System.out.println("Metodo no soportado para archivos planos");
    }

    @Override
    public void eliminar(int id) {
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", ");  
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
            System.out.println("El municipio con ID " + id + " ha sido eliminado con exito.");
        } else {
            System.out.println("No se encontro ningun municipio con el ID " + id + ".");
        }
    }

    
}
