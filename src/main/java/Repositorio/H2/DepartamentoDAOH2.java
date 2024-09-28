/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.ConexionFabrica;
import Fabrica.DepartamentoFabrica;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
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
public class DepartamentoDAOH2 implements IGestorDatos<Departamento>{
    
    private final DepartamentoFabrica departamentoFabrica;
    private final IGestorDatos<Pais> paisDAO;
    
    public DepartamentoDAOH2(){
        this.paisDAO = ConexionFabrica.getGestorDatos(Pais.class);
        this.departamentoFabrica = new DepartamentoFabrica();
    }
    
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
    public Departamento consultar(int id) {
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
                    String nombreDepartamento = resultados.getString("nombre");
                    
                    int paisId = resultados.getInt("pais_id");                   
                    Pais pais = paisDAO.consultar(paisId);
                                                                          
                    return departamentoFabrica.crearDepartamento(Departamentoid, nombreDepartamento, pais);

                } 
                
                return null;
                
            }
        
        }catch (SQLException excepcion) {
            
            System.err.println("Error al buscar el departamento en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Departamento> listar() {
        
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT d.id, d.nombre, p.id as pais_id, p.nombre as pais_nombre FROM Prueba.Departamento d JOIN Prueba.Pais p ON d.pais_id = p.id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                int paisId = resultado.getInt("pais_id");
                Pais pais = paisDAO.consultar(paisId);

                Departamento departamento = departamentoFabrica.crearDepartamento(id, nombre, pais);
                departamentos.add(departamento);
            }
            
            for(Departamento departamento : departamentos){
                System.out.println(departamento.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar los departamentos: " + excepcion.getMessage());
        }
        
        return departamentos;
    }

    @Override
    public void actualizar(Departamento departamento) {

        String sql = "UPDATE Prueba.DEPARTAMENTO SET nombre = ?, pais_id = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, departamento.getNombre());
            consulta.setInt(2, departamento.getPais().getId()); 
            consulta.setInt(3, departamento.getId());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El departamento ha sido actualizado con exito.");
            } else {
                System.out.println("No se encontro un departamento con el ID proporcionado.");
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
                System.out.println("No se encontro un departamento con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el departamento en la base de datos: " + excepcion.getMessage());
        }
    }

//    @Override
//    public void guardarEnArchivoBIN(Departamento departamento) {
//        
//        String ruta = "data/departamento.bin";
//        List<Departamento> departamentos = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                departamentos = (List<Departamento>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        departamentos.add(departamento);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(departamentos);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/departamento.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Departamento> departamentos = (List<Departamento>) objIn.readObject();
//
//            for (Departamento departamento : departamentos) {
//                System.out.println(departamento);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }

    
}
