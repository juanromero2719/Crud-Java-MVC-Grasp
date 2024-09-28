/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;


import Fabrica.CargoFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;

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
public class CargoDAOCSV implements IGestorDatos<Cargo>{
    
    private final CargoFabrica cargoFabrica;
    private final static String ARCHIVO_CSV = "data/cargo.csv";
    
    public CargoDAOCSV(){
        this.cargoFabrica = new CargoFabrica();
    }

    @Override
    public void crear(Cargo cargo) {
        
        boolean idExiste = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", ");
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim());
                    if (idExistente == cargo.getId()) {
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
                writer.write(cargo.getId() + ", " + cargo.getNombre());
                writer.newLine();
                System.out.println("Cargo agregado con exito.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + cargo.getId() + " ya existe. No se puede agregar el cargo.");
        }        
    }

    @Override
    public Cargo consultar(int id) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            
            linea = linea.trim();
            String[] datos = linea.split(",\\s*");

            if (datos.length >= 2) {
                int idCargo = Integer.parseInt(datos[0].trim()); 
                if (idCargo == id) {
                    String nombreCargo = datos[1].trim();
                    return cargoFabrica.crearCargo(idCargo, nombreCargo);
                }
            }
        }
       
    } catch (IOException e) {
        System.err.println("Error al leer el archivo CSV: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Formato de número inválido en el archivo CSV: " + e.getMessage());
    }

    return null;
    
    }

    @Override
    public List<Cargo> listar() {
        List<Cargo> cargos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 2) { 
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    cargos.add(cargoFabrica.crearCargo(id, nombre));
                } else {
                    System.err.println("Formato de linea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leidos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Cargo cargo : cargos){
            System.out.println(cargo);
        }

        return cargos;
    }

    @Override
    public void actualizar(Cargo cargo) {
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
            System.out.println("El cargo con ID " + id + " ha sido eliminado con exito.");
        } else {
            System.out.println("No se encontro ningun cargo con el ID " + id + ".");
        }
    }

}
