/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.EstudianteFabrica;
import Interface.IGestorDatos;
import Usuario.Estudiante;
import Usuario.Persona;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante_MCA
 */
public class EstudianteDAOCSV implements IGestorDatos<Estudiante>{
    
    private final static String ARCHIVO_CSV = "data/estudiante.csv";
    private final EstudianteFabrica estudianteFabrica;
    private final IGestorDatos<Persona> personaDAO;
    
    public EstudianteDAOCSV(){
        this.estudianteFabrica = new EstudianteFabrica();
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
    }

    @Override
    public void crear(Estudiante estudiante) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", "); 
                if (datos.length > 0) {
                    String idStr = datos[0].trim();
                    if (!idStr.isEmpty()) {  // Asegurarse de que el ID no sea una cadena vacía
                        int idExistente = Integer.parseInt(idStr); 
                        if (idExistente == estudiante.getId()) {
                            idExiste = true;
                            break;  
                        }
                    } else {
                        System.err.println("Error: Se encontró un ID vacío en el archivo CSV.");
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: El ID no es un número válido.");
        }

        if (!idExiste) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
                writer.write(estudiante.getId() + ", " + estudiante.getCodigo() + ", " + estudiante.getPrograma() + ", " + estudiante.getPromedio() + ", " + estudiante.getPersona());
                writer.newLine();
                System.out.println("Estudiante agregado con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + estudiante.getId() + " ya existe. No se puede agregar el estudiante.");
        }
    }



    @Override
    public Estudiante consultar(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", "); 
                if (partes.length < 5) {
                    System.err.println("Formato incorrecto en la línea: " + linea);
                    continue; 
                }

                int estudianteId = Integer.parseInt(partes[0].trim());
                String codigo = partes[1].trim();
                String programa = partes[2].trim();
                double promedio = Double.parseDouble(partes[3].trim());
                int personaId = Integer.parseInt(partes[4].trim()); 

                if (estudianteId == id) {
                    Persona persona = personaDAO.consultar(personaId);
                    return estudianteFabrica.crearEstudiante(estudianteId, codigo, programa, promedio, persona);
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer el archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear los datos: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Estudiante> listar() {
        List<Estudiante> estudiantes = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 5) { 
                    int id = Integer.parseInt(datos[0].trim());
                    String codigo = datos[1].trim();
                    String programa = datos[2].trim();
                    double promedio = Double.parseDouble(datos[3].trim());
                    int personaId = Integer.parseInt(datos[4].trim());

                    Persona persona = personaDAO.consultar(personaId);                                   
                    estudiantes.add(estudianteFabrica.crearEstudiante(id, codigo, programa, promedio, persona));
                    
                } 
            }
            System.out.println("Datos leídos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        return estudiantes;
    }

    @Override
    public void actualizar(Estudiante estudiante) {
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
                    String idStr = datos[0].trim();
                    if (!idStr.isEmpty()) {  
                        int idExistente = Integer.parseInt(idStr);
                        if (idExistente != id) {
                            lineas.add(linea); 
                        } else {
                            encontrado = true; 
                        }
                    } else {
                        System.err.println("Error: Se encontró un ID vacío en el archivo CSV.");
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: El ID no es un número válido.");
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
            System.out.println("El estudiante con ID " + id + " ha sido eliminado con exito.");
        } else {
            System.out.println("No se encontro ningun estudiante con el ID " + id + ".");
        }
    }
 
}   
