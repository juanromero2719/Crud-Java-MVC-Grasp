/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.CSV;

import Fabrica.ConexionFabrica;
import Fabrica.EmpleadoFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;
import Usuario.Empleado;
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
public class EmpleadoDAOCSV implements IGestorDatos<Empleado>{
    
    private final static String ARCHIVO_CSV = "data/empleado.csv";
    private final IGestorDatos<Persona> personaDAO;
    private final IGestorDatos<Cargo> cargoDAO;
    private final EmpleadoFabrica empleadoFabrica;
    
    public EmpleadoDAOCSV(){
        this.cargoDAO = ConexionFabrica.getGestorDatos(Cargo.class);
        this.empleadoFabrica = new EmpleadoFabrica();
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
    }

    @Override
    public void crear(Empleado empleado) {
        boolean idExiste = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  
                if (datos.length > 0) {
                    int idExistente = Integer.parseInt(datos[0].trim()); 
                    if (idExistente == empleado.getId()) {
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
                writer.write(empleado.getId() + ", " + empleado.getSalario() + ", " + empleado.getCargo().getId() + ", " + empleado.getPersona());
                writer.newLine();
                System.out.println("Empleado agregado con exito en el archivo CSV.");
            } catch (IOException excepcion) {
                System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
            }
        } else {
            System.out.println("El ID " + empleado.getId() + " ya existe. No se puede agregar el empleado.");
        }
    }

    @Override
    public Empleado consultar(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", "); 
                if (partes.length < 4) {
                    System.err.println("Formato incorrecto en la línea: " + linea);
                    continue; 
                }

                int empleadoId = Integer.parseInt(partes[0].trim());
                double salario = Double.parseDouble(partes[1].trim());
                int cargoId = Integer.parseInt(partes[2].trim());
                int personaId = Integer.parseInt(partes[3].trim()); 

                if (empleadoId == id) {
                    Cargo cargo = cargoDAO.consultar(cargoId);
                    Persona persona = personaDAO.consultar(personaId);
                    Empleado empleado = empleadoFabrica.crearEmpleado(empleadoId, cargo, salario, persona);
                    System.out.println(empleado.toString());
                    return empleado;
                }
            }
            System.out.println("No se encontro el empleado");
        } catch (IOException excepcion) {
            System.err.println("Error al leer el archivo CSV: " + excepcion.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear los datos: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                
                String[] datos = linea.split(", "); 
                
                if (datos.length == 4) { 
                    int id = Integer.parseInt(datos[0].trim());
                    double salario = Double.parseDouble(datos[1].trim());
                    int cargoId = Integer.parseInt(datos[2].trim());
                    int personaId = Integer.parseInt(datos[3].trim());


                    Cargo cargo = cargoDAO.consultar(cargoId);
                    Persona persona = personaDAO.consultar(personaId);

                    empleados.add(empleadoFabrica.crearEmpleado(id, cargo, salario, persona));
                } else {
                    System.err.println("Formato de línea incorrecto: " + linea);
                }
            }
            System.out.println("Datos leídos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
        
        for(Empleado empleado : empleados) {
            System.out.println(empleado);
        }

        return empleados;
    }

    @Override
    public void actualizar(Empleado empleado) {
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
                System.out.println("La empleado con ID " + id + " ha sido eliminada con exito.");
            } else {
                System.out.println("No se encontro ningun empleado con el ID " + id + ".");
            }
        }

   
    
}
