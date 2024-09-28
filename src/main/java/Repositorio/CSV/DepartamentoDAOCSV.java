/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.DepartamentoFabrica;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
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
public class DepartamentoDAOCSV implements IGestorDatos<Departamento>{
    
    private final IGestorDatos<Pais> paisDAO;
    private final DepartamentoFabrica departamentoFabrica;
    private final static String ARCHIVO_CSV = "data/departamento.csv";
    
    public DepartamentoDAOCSV(){
        this.departamentoFabrica = new DepartamentoFabrica();
        this.paisDAO = ConexionFabrica.getGestorDatos(Pais.class);
    }

    @Override
    public void crear(Departamento departamento) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim()); 
                    if (idExistente == departamento.getId()) {
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
                writer.write(departamento.getId() + ", " + departamento.getNombre() + ", " + departamento.getPais().getId());
                writer.newLine();
                System.out.println("Departamento agregado con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + departamento.getId() + " ya existe. No se puede agregar el departamento.");
        }
    }

    @Override
    public Departamento consultar(int id) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                int departamentoId = Integer.parseInt(partes[0].trim());
                String nombreDepartamento = partes[1].trim();
                int paisId = Integer.parseInt(partes[2].trim());

                if (departamentoId == id) {
                    Pais pais = paisDAO.consultar(paisId);
                    return departamentoFabrica.crearDepartamento(departamentoId, nombreDepartamento, pais);
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
    public List<Departamento> listar() {
        List<Departamento> departamentos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 3) {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    int paisId = Integer.parseInt(datos[2].trim());

                    Pais pais = paisDAO.consultar(paisId);
                 
                    departamentos.add(departamentoFabrica.crearDepartamento(id, nombre, pais));
                } else {
                    System.err.println("Formato de línea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leídos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Departamento departamento : departamentos) {
            System.out.println(departamento);
        }

        return departamentos;
    }


    @Override
    public void actualizar(Departamento t) {
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
            System.out.println("El departamento con ID " + id + " ha sido eliminado con exito.");
        } else {
            System.out.println("No se encontro ningun departamento con el ID " + id + ".");
        }
    }

    
}
