/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio;

import Conexion.Conexion;
import Interface.IGestorDatos;
import Ubicacion.Pais;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
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
public class PaisDAO implements IGestorDatos<Pais> {
    
    private static String ARCHIVO_CSV = "data/pais.csv";

    @Override
    public void crear(Pais pais) {
        try(Connection conexion = Conexion.conectar()){
            
            conexion.setAutoCommit(false);
            String sql = "INSERT INTO Prueba.Pais (id, nombre) VALUES (?, ?)";
                        
            try(PreparedStatement consulta = conexion.prepareStatement(sql)){
                
                consulta.setInt(1, pais.getId());
                consulta.setString(2, pais.getNombre());               
                consulta.executeUpdate();
                
                conexion.commit();
                System.out.println("Pais agregado con exito");
                
                
            }catch(SQLException excepcion){
            
                System.err.println("Error: " + excepcion.getMessage());
                conexion.rollback();
                
            }finally{           
                conexion.setAutoCommit(true);
            }
            
        }catch(Exception excepcion){
            
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Pais leer(int id) {

        String sql = "SELECT * FROM Prueba.Pais WHERE id = ?";
        
        try (Connection conexion = Conexion.conectar();
        PreparedStatement consulta = conexion.prepareStatement(sql)) {
        
        consulta.setInt(1, id);
        
            try (ResultSet resultados = consulta.executeQuery()) {
                
                if (resultados.next()) {

                    int paisId = resultados.getInt("id");
                    String nombrePais = resultados.getString("nombre");          
                    Pais pais = new Pais(paisId, nombrePais);
                    return pais;

                } else {
                    System.out.println("No se encontro un pais con el nombre proporcionado.");
                    return null;
                }
            }
        
        }catch (SQLException excepcion) {
            
            System.err.println("Error al buscar el país en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public void listar() {
        
        List<Pais> paises = new ArrayList<>();
        
        try(Connection conexion = Conexion.conectar()){
            
            String sql = "Select * from Prueba.PAIS";
            
            try (PreparedStatement consulta = conexion.prepareStatement(sql);     
                ResultSet resultados = consulta.executeQuery()) {
                
                while (resultados.next()) {
                                         
                    int id = resultados.getInt("id");
                    String nombre = resultados.getString("nombre");
                    Pais pais = new Pais(id, nombre);
                    paises.add(pais);
                        
                }
                
                for(Pais pais : paises){                    
                    System.out.println(pais.toString());
                }
                    
            }catch (SQLException excepcion) {
                
                System.err.println("Error al listar los paises: " + excepcion.getMessage());
            }
            finally {
                
                conexion.setAutoCommit(true);
                conexion.close();
            }
                    
            
        }catch(Exception excepcion){           
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());  
        }
    }

    @Override
    public void actualizar(Pais pais) {
        
        try (Connection conexion = Conexion.conectar()) {
       
            conexion.setAutoCommit(false);
            String sql = "UPDATE Prueba.PAIS SET nombre = ? WHERE id = ?";
        
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setString(1, pais.getNombre());  
                consulta.setInt(2, pais.getId());

                int filasAfectadas = consulta.executeUpdate();

                if (filasAfectadas > 0) {
                    
                    conexion.commit();                                 
                    System.out.println("País actualizado con éxito: " + pais);                   
                }

            }catch (SQLException excepcion) {
                
                conexion.rollback();
                System.err.println("Error al actualizar el país en la base de datos: " + excepcion.getMessage());
            } finally {
                
                conexion.setAutoCommit(true);
            }

        }catch (SQLException excepcion) {
            
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {

        try(Connection conexion = Conexion.conectar()){
                                      
            conexion.setAutoCommit(false);
            String sql = "DELETE FROM Prueba.PAIS WHERE id = ?";
                
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                    
                consulta.setInt(1, id);                   
                consulta.executeUpdate();
                conexion.commit();
                System.out.println("Pais borrado");       
                
            }catch (SQLException excepcion) {
                    
                System.err.println("Error al borrar la plataforma en la base de datos: " + excepcion.getMessage());
            }finally {
                
                conexion.setAutoCommit(true);
                conexion.close();
            }
                                                                
        }catch(Exception excepcion){
            
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());  
        }
    }

    @Override
    public void guardarEnArchivoCSV(Pais pais) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
            writer.write(pais.getId() + "," + pais.getNombre());
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
    public void guardarEnArchivoBIN(Pais pais) {
        
        String ruta = "data/pais.bin";
        List<Pais> paises = new ArrayList<>();

        File archivo = new File(ruta);
        if (archivo.exists()) {
            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
                
                paises = (List<Pais>) objIn.readObject();
                
            } catch (IOException | ClassNotFoundException excepcion) {
                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
            }
        }

        paises.add(pais);
        
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
            objOut.writeObject(paises);
            
        } catch (IOException excepcion) {
            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
        }
    }

    @Override
    public void leerDesdeArchivoBIN() {
        String ruta = "data/pais.bin";

        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
            List<Pais> paises = (List<Pais>) objIn.readObject();

            for (Pais pais : paises) {
                System.out.println(pais);
            }

        } catch (IOException | ClassNotFoundException excepcion) {
            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
        }
    }


    
}
