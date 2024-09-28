/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.PersonaFabrica;
import Interface.IGestorDatos;
import Ubicacion.Direccion;
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
public class PersonaDAOCSV implements IGestorDatos<Persona>{
    
    private static String ARCHIVO_CSV = "data/persona.csv";
    private final PersonaFabrica personaFabrica;
    private final IGestorDatos<Direccion> direccionDAO;
    
    public PersonaDAOCSV(){
        this.personaFabrica = new PersonaFabrica();
        this.direccionDAO = ConexionFabrica.getGestorDatos(Direccion.class);
    }

    @Override
    public void crear(Persona persona) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(", ");  
                if (datos.length > 0) {
                    String idStr = datos[0].trim();
                    if (!idStr.isEmpty()) {
                        int idExistente = Integer.parseInt(idStr);  
                        if (idExistente == persona.getId()) {
                            idExiste = true;
                            break;  
                        }
                    } else {
                        System.err.println("Error: Se encontro una linea con un ID vacio.");
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: El ID de la direccion no es un numero valido.");
        }

        if (!idExiste) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
                writer.write(persona.getId() + ", " + persona.getNombres() + ", " + persona.getApellidos() + ", " + persona.getDireccion().getId());
                writer.newLine();
                System.out.println("Persona agregada con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + persona.getId() + " ya existe. No se puede agregar la persona.");
        }
    }

    @Override
    public Persona consultar(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                if (partes.length < 4) {
                    System.err.println("Formato incorrecto en la línea: " + linea);
                    continue; 
                }

                int personaId = Integer.parseInt(partes[0].trim());
                String nombres = partes[1].trim();
                String apellidos = partes[2].trim();
                int direccionId = Integer.parseInt(partes[3].trim());

                if (personaId == id) {
                    Direccion direccion = direccionDAO.consultar(direccionId);
                    return  personaFabrica.crearPersona(personaId, nombres, apellidos, direccion);
                }
            }            
        } catch (IOException excepcion) {
            System.err.println("Error al leer el archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear el ID o la dirección: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Persona> listar() {
        List<Persona> personas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 4) { 
                    int id = Integer.parseInt(datos[0].trim());
                    String nombres = datos[1].trim();
                    String apellidos = datos[2].trim();
                    int direccionId = Integer.parseInt(datos[3].trim());

                    Direccion direccion = direccionDAO.consultar(direccionId);
                    personas.add(personaFabrica.crearPersona(id, nombres, apellidos, direccion));
                } else {
                    System.err.println("Formato de linea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leidos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Persona persona : personas) {
            System.out.println(persona);
        }

        return personas;
    }

    @Override
    public void actualizar(Persona persona) {
        System.out.println("Metodo no soportado para archivos planos");
    }

    @Override
    public void eliminar(int id) {
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(", ");  // Ajustado al separador utilizado en el archivo CSV
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim());
                    if (idExistente != id) {
                        lineas.add(linea);  // Agregar las líneas que no coinciden con el ID
                    } else {
                        encontrado = true;  // Marca como encontrado si coincide el ID
                    }
                }
            }
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }

        // Escribir las líneas restantes de nuevo en el archivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException excepcion) {
            System.err.println("Error al escribir en archivo CSV: " + excepcion.getMessage());
        }

        // Mensajes de éxito o de no encontrado
        if (encontrado) {
            System.out.println("La persona con ID " + id + " ha sido eliminada con exito.");
        } else {
            System.out.println("No se encontro ninguna persona con el ID " + id + ".");
        }
    }

    
}
