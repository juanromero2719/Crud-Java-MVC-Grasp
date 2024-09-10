/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio;

import Conexion.Conexion;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
import Ubicacion.Direccion;
import Ubicacion.Municipio;
import Ubicacion.Pais;
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
 * @author ASUS
 */
public class DireccionDAO implements IGestorDatos<Direccion> {
    
    private static String ARCHIVO_CSV = "data/direccion.csv";

    @Override
    public void crear(Direccion direccion) {

        String sql = "INSERT INTO Prueba.DIRECCION (id, municipio_id, calle, carrera, coordenada, descripcion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setInt(1, direccion.getId());
                consulta.setInt(2, direccion.getMunicipio().getId());
                consulta.setString(3, direccion.getCalle());
                consulta.setString(4, direccion.getCarrera());
                consulta.setString(5, direccion.getCoordenada());
                consulta.setString(6, direccion.getDescripcion());

                consulta.executeUpdate();
                conexion.commit();
                System.out.println("Direccion agregada con exito");

            } catch (SQLException excepcion) {
                System.err.println("Error al insertar la direccion: " + excepcion.getMessage());
                conexion.rollback();
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Direccion leer(int id) {
        
        String sql = "SELECT d.id AS direccion_id, d.calle, d.carrera, d.coordenada, d.descripcion, " +
                 "m.id AS municipio_id, m.nombre AS municipio_nombre, " +
                 "dp.id AS departamento_id, dp.nombre AS departamento_nombre, " +
                 "p.id AS pais_id, p.nombre AS pais_nombre " +
                 "FROM Prueba.DIRECCION d " +
                 "JOIN Prueba.MUNICIPIO m ON d.municipio_id = m.id " +
                 "JOIN Prueba.DEPARTAMENTO dp ON m.departamento_id = dp.id " +
                 "JOIN Prueba.PAIS p ON dp.pais_id = p.id " +
                 "WHERE d.id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {

                if (resultados.next()) {

                    int direccionId = resultados.getInt("direccion_id");
                    String direccionCalle = resultados.getString("calle");
                    String direccionCarrera = resultados.getString("carrera");
                    String coordenada = resultados.getString("coordenada");
                    String descripcion = resultados.getString("descripcion");

                    int municipioId = resultados.getInt("municipio_id");
                    MunicipioDAO municipioDAO = new MunicipioDAO();
                    Municipio municipio = municipioDAO.leer(municipioId);
                   
                    return new Direccion(direccionId, municipio, direccionCalle, direccionCarrera, coordenada, descripcion);

                } else {
                    System.out.println("No se encontro una direccion con los datos proporcionados.");
                    return null;
                }

            } catch (SQLException excepcion) {
                System.err.println("Error al buscar la direccion en la base de datos: " + excepcion.getMessage());
                return null;
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public void listar() {

        String sql = "SELECT d.id AS direccion_id, d.calle, d.carrera, d.coordenada, d.descripcion, " +
                     "m.id AS municipio_id, m.nombre AS municipio_nombre, " +
                     "dp.id AS departamento_id, dp.nombre AS departamento_nombre, " +
                     "p.id AS pais_id, p.nombre AS pais_nombre " +
                     "FROM Prueba.DIRECCION d " +
                     "JOIN Prueba.MUNICIPIO m ON d.municipio_id = m.id " +
                     "JOIN Prueba.DEPARTAMENTO dp ON m.departamento_id = dp.id " +
                     "JOIN Prueba.PAIS p ON dp.pais_id = p.id";

        List<Direccion> direcciones = new ArrayList<>();

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {

            while (resultados.next()) {

                int direccionId = resultados.getInt("direccion_id");
                String calle = resultados.getString("calle");
                String carrera = resultados.getString("carrera");
                String coordenada = resultados.getString("coordenada");
                String descripcion = resultados.getString("descripcion");

                int paisId = resultados.getInt("pais_id");
                String paisNombre = resultados.getString("pais_nombre");
                Pais pais = new Pais(paisId, paisNombre);

                int departamentoId = resultados.getInt("departamento_id");
                String departamentoNombre = resultados.getString("departamento_nombre");
                Departamento departamento = new Departamento(departamentoId, departamentoNombre, pais);

                int municipioId = resultados.getInt("municipio_id");
                String municipioNombre = resultados.getString("municipio_nombre");
                Municipio municipio = new Municipio(municipioNombre, municipioId, departamento);

                Direccion direccion = new Direccion(direccionId, municipio, calle, carrera, coordenada, descripcion);
                direcciones.add(direccion);
            }

            for (Direccion direccion : direcciones) {
                System.out.println(direccion.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar las direcciones desde la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void actualizar(Direccion direccion) {
        String sql = "UPDATE Prueba.DIRECCION SET calle = ?, carrera = ?, coordenada = ?, descripcion = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, direccion.getCalle());
            consulta.setString(2, direccion.getCarrera());
            consulta.setString(3, direccion.getCoordenada());
            consulta.setString(4, direccion.getDescripcion());
            consulta.setInt(5, direccion.getId());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Direccion actualizada con exito.");
            } else {
                System.out.println("No se encontro ninguna direccion con el id proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al actualizar la direccion en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        
        String sql = "DELETE FROM Prueba.DIRECCION WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Direccion eliminada con exito.");
            } else {
                System.out.println("No se encontró ninguna direccion con el id proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar la direccion en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void guardarEnArchivoCSV(Direccion direccion) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
            writer.write(direccion.getId() + ", " + direccion.getCalle() + ", " + direccion.getCarrera() + ", " + direccion.getCoordenada() + ", " + direccion.getDescripcion() + ", " + direccion.getMunicipio().getNombre());
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
    public void guardarEnArchivoBIN(Direccion direccion) {
        
        String ruta = "data/direccion.bin";
        List<Direccion> direcciones = new ArrayList<>();

        File archivo = new File(ruta);
        if (archivo.exists()) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
                
                direcciones = (List<Direccion>) objIn.readObject();
                
            } catch (IOException | ClassNotFoundException excepcion) {
                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
            }
        }
        
        direcciones.add(direccion);
        
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
            objOut.writeObject(direcciones);
            
        } catch (IOException excepcion) {
            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
        }
    }

    @Override
    public void leerDesdeArchivoBIN() {
        
        String ruta = "data/direccion.bin";

        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
            List<Direccion> direcciones = (List<Direccion>) objIn.readObject();

            for (Direccion direccion : direcciones) {
                System.out.println(direccion);
            }

        } catch (IOException | ClassNotFoundException excepcion) {
            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
        }
    }
    
}
