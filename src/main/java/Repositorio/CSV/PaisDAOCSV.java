/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.PaisFabrica;
import Interface.IGestorDatos;
import Ubicacion.Pais;
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
public class PaisDAOCSV implements IGestorDatos<Pais>{
    
    private static String ARCHIVO_CSV = "data/pais.csv";
    private final PaisFabrica paisFabrica;
    
    public PaisDAOCSV(){
        this.paisFabrica = new PaisFabrica();
    }

    @Override
    public void crear(Pais pais) {
        
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", ");
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim());
                    if (idExistente == pais.getId()) {
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
                writer.write(pais.getId() + ", " + pais.getNombre());
                writer.newLine();
                System.out.println("Pais agregado con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + pais.getId() + " ya existe. No se puede agregar el pais.");
        }
    }

    @Override
    public Pais consultar(int id) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(","); 
                
                if (partes.length < 2) {
                    System.err.println("Formato incorrecto en la linea: " + linea);
                    continue; 
                }

                int paisId = Integer.parseInt(partes[0].trim());
                String nombrePais = partes[1].trim();

                if (paisId == id) {
                    return paisFabrica.crearPais(paisId, nombrePais);
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
    public List<Pais> listar() {
        List<Pais> paises = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(","); 
                
                if (datos.length == 2) {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();

                    paises.add(paisFabrica.crearPais(id, nombre));
                } else {
                    System.err.println("Formato de linea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leidos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Pais pais : paises) {
            System.out.println(pais);
        }

        return paises;
    }
    
    @Override
    public void actualizar(Pais pais) {
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
            System.out.println("El pais con ID " + id + " ha sido eliminado con exito.");
        } else {
            System.out.println("No se encontro ningun pais con el ID " + id + ".");
        }
    }

   
}
