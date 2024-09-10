/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio;

import Conexion.Conexion;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
import Ubicacion.Pais;
import Usuario.Cargo;
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
public class DepartamentoDAO implements IGestorDatos<Departamento>{
    
    private static String ARCHIVO_CSV = "data/departamento.csv";

    @Override
    public void crear(Departamento departamento) {

        String sql = "INSERT INTO Prueba.Departamento (id, nombre, pais_id) VALUES (?, ?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setInt(1, departamento.getId());
                consulta.setString(2, departamento.getNombre());
                consulta.setInt(3, departamento.getPais().getId());
                
                consulta.executeUpdate();
                conexion.commit();
                System.out.println("Departamento agregado con exito");

            } catch (SQLException excepcion) {
                System.err.println("Error: " + excepcion.getMessage());
                conexion.rollback();
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (Exception excepcion) {
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Departamento leer(int id) {
        String sql = "SELECT d.id, d.nombre, d.pais_id, p.nombre as pais_nombre " +
                 "FROM Prueba.Departamento d " +
                 "JOIN Prueba.Pais p ON d.pais_id = p.id " +
                 "WHERE d.id = ?"; 
        
        try (Connection conexion = Conexion.conectar();
        PreparedStatement consulta = conexion.prepareStatement(sql)) {
        
            consulta.setInt(1, id);
        
            try (ResultSet resultados = consulta.executeQuery()) {
                
                if (resultados.next()) {
                    
                    int Departamentoid = resultados.getInt("id");
                    String nombrePais = resultados.getString("nombre");
                    
                    PaisDAO paisDAO = new PaisDAO();
                    int paisId = resultados.getInt("pais_id");                   
                    Pais pais = paisDAO.leer(paisId);
                    
                    return new Departamento(Departamentoid, nombrePais, pais);

                } else {

                    System.out.println("No se encontro un departamento con el nombre proporcionado.");
                    return null;
                }
            }
        
        }catch (SQLException excepcion) {
            
            System.err.println("Error al buscar el departamento en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public void listar() {
        
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT d.id, d.nombre, p.id as pais_id, p.nombre as pais_nombre FROM Prueba.Departamento d JOIN Prueba.Pais p ON d.pais_id = p.id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                int paisId = resultado.getInt("pais_id");
                String paisNombre = resultado.getString("pais_nombre");
                Pais pais = new Pais(paisId, paisNombre);

                Departamento departamento = new Departamento(id, nombre, pais);
                departamentos.add(departamento);
            }
            
            for(Departamento departamento : departamentos){
                System.out.println(departamento.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar los departamentos: " + excepcion.getMessage());
        }
    }

    @Override
    public void actualizar(Departamento departamento) {

        String sql = "UPDATE Prueba.DEPARTAMENTO SET nombre = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
           
            consulta.setString(1, departamento.getNombre());
            consulta.setInt(2, departamento.getId());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El departamento ha sido actualizado con éxito.");
            } else {
                System.out.println("No se encontró un departamento con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al editar el departamento en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM Prueba.DEPARTAMENTO WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            
            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El departamento ha sido eliminado con exito.");
            } else {
                System.out.println("No se encontró un departamento con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el departamento en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void guardarEnArchivoCSV(Departamento departamento) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
            writer.write(departamento.getId() + ", " + departamento.getNombre() + ", " + departamento.getPais().getNombre());
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
    public void guardarEnArchivoBIN(Departamento departamento) {
        
        String ruta = "data/departamento.bin";
        List<Departamento> departamentos = new ArrayList<>();

        File archivo = new File(ruta);
        if (archivo.exists()) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
                
                departamentos = (List<Departamento>) objIn.readObject();
                
            } catch (IOException | ClassNotFoundException excepcion) {
                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
            }
        }
        
        departamentos.add(departamento);
        
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
            objOut.writeObject(departamentos);
            
        } catch (IOException excepcion) {
            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
        }
    }

    @Override
    public void leerDesdeArchivoBIN() {
        
        String ruta = "data/departamento.bin";

        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
            List<Departamento> departamentos = (List<Departamento>) objIn.readObject();

            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }

        } catch (IOException | ClassNotFoundException excepcion) {
            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
        }
    }

    
}
