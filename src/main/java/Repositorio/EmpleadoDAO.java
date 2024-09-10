/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio;

import Conexion.Conexion;
import Interface.IGestorDatos;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wrydmoon
 */
public class EmpleadoDAO implements IGestorDatos<Empleado>{
    
    private static String ARCHIVO_CSV = "data/empleado.csv";

    @Override
    public void crear(Empleado empleado) {
      
        String sql = "INSERT INTO Prueba.EMPLEADO (id, salario, cargo_id, persona_id) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar()){
            conexion.setAutoCommit(false); 
            
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            
            consulta.setInt(1, empleado.getId());
            consulta.setDouble(2, empleado.getSalario());
            consulta.setInt(3, empleado.getCargo().getId());
            consulta.setInt(4, empleado.getId());
                     
            consulta.executeUpdate();
            conexion.commit();
            System.out.println("Empleado agregado con exito.");
                
            }catch (SQLException excepcion) {
                System.err.println("Error al insertar el empleado: " + excepcion.getMessage());
                conexion.rollback();
            } finally {
                conexion.setAutoCommit(true);
            }
          
        } catch (SQLException excepcion) {
            System.err.println("Error al crear el empleado: " + excepcion.getMessage());
        }
    }

    @Override
    public Empleado leer(int id) {
        
        String sql = "SELECT e.id AS empleado_id, e.salario, e.cargo_id, e.persona_id, " +
                     "p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id, " +
                     "c.nombre AS cargo_nombre " +
                     "FROM Prueba.EMPLEADO e " +
                     "JOIN Prueba.PERSONA p ON e.persona_id = p.id " +
                     "JOIN Prueba.CARGO c ON e.cargo_id = c.id " +
                     "WHERE e.id = ?"; 

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {
                if (resultados.next()) {
                    
                    double salario = resultados.getDouble("salario");
                    
                    CargoDAO cargoDAO = new CargoDAO();
                    int cargoId = resultados.getInt("cargo_id");
                    Cargo cargo = cargoDAO.leer(cargoId);
                    
                    PersonaDAO personaDAO = new PersonaDAO(); 
                    int personaId = resultados.getInt("persona_id");
                    Persona persona = personaDAO.leer(personaId);

                    return new Empleado(id, cargo, salario, persona);
                    
                } else {
                    System.out.println("No se encontró un empleado con el id proporcionado.");
                    return null;
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar el empleado: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public void listar() {
        
        String sql = "SELECT e.id AS empleado_id, e.salario, e.cargo_id, e.persona_id, " +
                     "p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id, " +
                     "c.nombre AS cargo_nombre " +
                     "FROM Prueba.EMPLEADO e " +
                     "JOIN Prueba.PERSONA p ON e.persona_id = p.id " +
                     "JOIN Prueba.CARGO c ON e.cargo_id = c.id";

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {

            while (resultados.next()) {
                
                double salario = resultados.getDouble("salario");
                int id = resultados.getInt("empleado_id");
                
                CargoDAO cargoDAO = new CargoDAO();
                int cargoId = resultados.getInt("cargo_id");
                Cargo cargo = cargoDAO.leer(cargoId);
                
                PersonaDAO personaDAO = new PersonaDAO();
                int personaId = resultados.getInt("persona_id");
                Persona persona = personaDAO.leer(personaId);

                Empleado empleado = new Empleado(id, cargo, salario, persona);

                empleados.add(empleado);
            }
            
            for(Empleado empleado : empleados){
                System.out.println(empleado.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar los empleados: " + excepcion.getMessage());
        }

    }

    @Override
    public void actualizar(Empleado empleado) {
        
        String sqlEmpleado = "UPDATE Prueba.EMPLEADO SET salario = ?, cargo_id = ? WHERE id = ?";
        
        try (Connection conexion = Conexion.conectar();
             PreparedStatement consultaEmpleado = conexion.prepareStatement(sqlEmpleado)) {
            
            consultaEmpleado.setDouble(1, empleado.getSalario());
            consultaEmpleado.setInt(2, empleado.getCargo().getId());
            consultaEmpleado.setInt(3, empleado.getId());

            int filasAfectadasEmpleado = consultaEmpleado.executeUpdate();
            if (filasAfectadasEmpleado > 0) {
                System.out.println("Empleado actualizado con exito.");
            } else {
                System.out.println("No se encontró el empleado para actualizar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al editar el empleado: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        
        String sql = "DELETE FROM Prueba.EMPLEADO WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Empleado borrado exitosamente.");
            } else {
                System.out.println("No se encontró el empleado para eliminar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el empleado: " + excepcion.getMessage());
        }
    }

    @Override
    public void guardarEnArchivoCSV(Empleado empleado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
            writer.write(empleado.getId() + ", " + empleado.getSalario() + ", " + empleado.getCargo().getNombre() + ", " + empleado.getPersona());
            writer.newLine();          
        } catch (IOException excepcion) {
            System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
        }
    }

    @Override
    public void leerDesdeArchivoCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("Datos leidos del archivo " + ARCHIVO_CSV);
        } catch (IOException excepcion) {
            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
        }
    }

    @Override
    public void guardarEnArchivoBIN(Empleado empleado) {
        
        String ruta = "data/empleado.bin";
        List<Empleado> empleados = new ArrayList<>();

        File archivo = new File(ruta);
        if (archivo.exists()) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
                
                empleados = (List<Empleado>) objIn.readObject();
                
            } catch (IOException | ClassNotFoundException excepcion) {
                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
            }
        }
        
        empleados.add(empleado);
        
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
            objOut.writeObject(empleados);
            
        } catch (IOException excepcion) {
            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
        }
    }

    @Override
    public void leerDesdeArchivoBIN() {
        
        String ruta = "data/empleado.bin";

        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
            List<Empleado> empleados = (List<Empleado>) objIn.readObject();

            for (Empleado empleado : empleados) {
                System.out.println(empleado);
            }

        } catch (IOException | ClassNotFoundException excepcion) {
            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
        }
        
    }

   
    
}
