/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.PaisFabrica;
import Interface.IGestorDatos;
import Ubicacion.Pais;
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
public class PaisDAOH2 implements IGestorDatos<Pais> {
    
    private final PaisFabrica paisFabrica;
    
    public PaisDAOH2(){
        this.paisFabrica = new PaisFabrica();
    }
    
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
    public Pais consultar(int id) {

        String sql = "SELECT * FROM Prueba.Pais WHERE id = ?";
        
        try (Connection conexion = Conexion.conectar();
        PreparedStatement consulta = conexion.prepareStatement(sql)) {
        
        consulta.setInt(1, id);
        
            try (ResultSet resultados = consulta.executeQuery()) {
                
                if (resultados.next()) {

                    int paisId = resultados.getInt("id");
                    String nombrePais = resultados.getString("nombre");
                    
                    return paisFabrica.crearPais(paisId, nombrePais);

                }                   
                return null;
                
            }
        
        }catch (SQLException excepcion) {
            
            System.err.println("Error al buscar el pais en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Pais> listar() {
        
        List<Pais> paises = new ArrayList<>();
        
        try(Connection conexion = Conexion.conectar()){
            
            String sql = "Select * from Prueba.PAIS";
            
            try (PreparedStatement consulta = conexion.prepareStatement(sql);     
                ResultSet resultados = consulta.executeQuery()) {
                
                while (resultados.next()) {
                                         
                    int id = resultados.getInt("id");
                    String nombre = resultados.getString("nombre");

                    paises.add(paisFabrica.crearPais(id, nombre));
                        
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
        
        return paises;
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
                    System.out.println("Pais actualizado con exito");                   
                }else {
                    System.out.println("No se encontro un pais con el ID proporcionado.");
                }

            }catch (SQLException excepcion) {
                
                conexion.rollback();
                System.err.println("Error al actualizar el pais en la base de datos: " + excepcion.getMessage());
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
                int filasAfectadas = consulta.executeUpdate();
                
                if (filasAfectadas > 0) {
                System.out.println("El pais ha sido eliminado con exito.");
                } else {
                    System.out.println("No se encontro un pais con el ID proporcionado.");
                }
                
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

//    @Override
//    public void guardarEnArchivoBIN(Pais pais) {
//        
//        String ruta = "data/pais.bin";
//        List<Pais> paises = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                paises = (List<Pais>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//
//        paises.add(pais);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(paises);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        String ruta = "data/pais.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Pais> paises = (List<Pais>) objIn.readObject();
//
//            for (Pais pais : paises) {
//                System.out.println(pais);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }


    
}
