/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.ConexionFabrica;
import Fabrica.EmpleadoFabrica;
import Interface.IGestorDatos;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;
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
public class EmpleadoDAOH2 implements IGestorDatos<Empleado>{
    
    private final IGestorDatos<Cargo> cargoDAO;
    private final IGestorDatos<Persona> personaDAO;
    private final EmpleadoFabrica empleadoFabrica;
    
    public EmpleadoDAOH2(){
        this.cargoDAO = ConexionFabrica.getGestorDatos(Cargo.class);
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
        this.empleadoFabrica = new EmpleadoFabrica();
    }
    
    @Override
    public void crear(Empleado empleado) {
      
        String sql = "INSERT INTO Prueba.EMPLEADO (id, salario, cargo_id, persona_id) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar()){
            conexion.setAutoCommit(false); 
            
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            
            consulta.setInt(1, empleado.getId());
            consulta.setDouble(2, empleado.getSalario());
            consulta.setInt(3, empleado.getCargo().getId());
            consulta.setInt(4, empleado.getPersona());
                     
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
    public Empleado consultar(int id) {
        
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
                    int cargoId = resultados.getInt("cargo_id");                                      
                    int personaId = resultados.getInt("persona_id");
                    
                    Persona persona = personaDAO.consultar(personaId);
                    Cargo cargo = cargoDAO.consultar(cargoId);
                    return empleadoFabrica.crearEmpleado(id, cargo, salario, persona);
                    
                } else {
                    return null;
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar el empleado: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Empleado> listar() {
        
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
                
                int cargoId = resultados.getInt("cargo_id");
                Cargo cargo = cargoDAO.consultar(cargoId);
                
                int personaId = resultados.getInt("persona_id");
                Persona persona = personaDAO.consultar(personaId);
              
                empleados.add(empleadoFabrica.crearEmpleado(id, cargo, salario, persona));
            }
            
            for(Empleado empleado : empleados){
                System.out.println(empleado.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar los empleados: " + excepcion.getMessage());
        }
        
        return empleados;

    }

    @Override
    public void actualizar(Empleado empleado) {
        String sqlEmpleado = "UPDATE Prueba.EMPLEADO SET salario = ?, cargo_id = ?, persona_id = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consultaEmpleado = conexion.prepareStatement(sqlEmpleado)) {

            consultaEmpleado.setDouble(1, empleado.getSalario());
            consultaEmpleado.setInt(2, empleado.getCargo().getId());
            consultaEmpleado.setInt(3, empleado.getPersona()); 
            consultaEmpleado.setInt(4, empleado.getId());

            int filasAfectadasEmpleado = consultaEmpleado.executeUpdate();
            if (filasAfectadasEmpleado > 0) {
                System.out.println("Empleado actualizado con exito.");
            } else {
                System.out.println("No se encontro el empleado para actualizar.");
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
                System.out.println("No se encontro el empleado para eliminar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el empleado: " + excepcion.getMessage());
        }
    }


//    @Override
//    public void guardarEnArchivoBIN(Empleado empleado) {
//        
//        String ruta = "data/empleado.bin";
//        List<Empleado> empleados = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                empleados = (List<Empleado>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        empleados.add(empleado);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(empleados);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/empleado.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Empleado> empleados = (List<Empleado>) objIn.readObject();
//
//            for (Empleado empleado : empleados) {
//                System.out.println(empleado);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//        
//    }

   
    
}
