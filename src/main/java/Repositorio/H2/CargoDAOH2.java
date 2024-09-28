/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.CargoFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;
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
public class CargoDAOH2 implements IGestorDatos<Cargo>{
    
    private final CargoFabrica cargoFabrica;
    
    public CargoDAOH2(){
        this.cargoFabrica = new CargoFabrica();
    }
    
    @Override
    public void crear(Cargo cargo) {
        
        String sql = "INSERT INTO Prueba.Cargo (id, nombre) VALUES (?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setInt(1, cargo.getId());
                consulta.setString(2, cargo.getNombre());

                consulta.executeUpdate();

                conexion.commit();
                System.out.println("Cargo agregado con exito.");
                
            } catch (SQLException excepcion) {

                System.err.println("Error al insertar el cargo: " + excepcion.getMessage());
                conexion.rollback();             
                
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (Exception excepcion) {

            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Cargo consultar(int id) {
        
        String sql = "SELECT * FROM Prueba.Cargo WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {

                if (resultados.next()) {

                    int Cargoid = resultados.getInt("id");
                    String nombreCargo = resultados.getString("nombre");                                                                   
                    return cargoFabrica.crearCargo(Cargoid, nombreCargo);  

                }   
                
                return null;               
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar el cargo en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Cargo> listar() {
        
        List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM Prueba.Cargo";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                
                cargos.add(cargoFabrica.crearCargo(id, nombre));
            }

            for (Cargo cargo : cargos) {
                System.out.println(cargo.toString());
            }
     
        } catch (SQLException excepcion) {
            System.err.println("Error al listar los cargos: " + excepcion.getMessage());
        }
        
        return cargos;

    }

    @Override
    public void actualizar(Cargo cargo) {
        
        String sql = "UPDATE Prueba.Cargo SET nombre = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, cargo.getNombre());  
            consulta.setInt(2, cargo.getId());   

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El cargo ha sido actualizado con exito.");
            } else {
                System.out.println("No se encontro un cargo con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al editar el cargo en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        
        String sql = "DELETE FROM Prueba.Cargo WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El cargo ha sido eliminado con exito.");
            } else {
                System.out.println("No se encontro un cargo con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el cargo en la base de datos: " + excepcion.getMessage());
        }
    }


//    @Override
//    public void guardarEnArchivoBIN(Cargo cargo) {
//        
//        String ruta = "data/cargo.bin";
//        List<Cargo> cargos = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                cargos = (List<Cargo>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        cargos.add(cargo);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(cargos);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/cargo.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Cargo> cargos = (List<Cargo>) objIn.readObject();
//
//            for (Cargo cargo : cargos) {
//                System.out.println(cargo);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }

    

}
