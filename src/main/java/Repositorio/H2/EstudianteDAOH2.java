/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.ConexionFabrica;
import Fabrica.EstudianteFabrica;
import Interface.IGestorDatos;
import Usuario.Estudiante;
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
public class EstudianteDAOH2 implements IGestorDatos<Estudiante>{
    
    private final IGestorDatos<Persona> personaDAO;
    private final EstudianteFabrica estudianteFabrica;
    
    public EstudianteDAOH2(){
        this.estudianteFabrica = new EstudianteFabrica();
        this.personaDAO = ConexionFabrica.getGestorDatos(Persona.class);
    }
    
    @Override
    public void crear(Estudiante estudiante) {

        String sql = "INSERT INTO Prueba.Estudiante (id, codigo, programa, promedio, persona_id) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, estudiante.getId());
                consulta.setString(2, estudiante.getCodigo());
                consulta.setString(3, estudiante.getPrograma());
                consulta.setDouble(4, estudiante.getPromedio());
                consulta.setInt(5, estudiante.getPersona());

                consulta.executeUpdate();
                conexion.commit();
                System.out.println("Estudiante agregado con exito.");

            } catch (SQLException excepcion) {
                System.err.println("Error al insertar el estudiante: " + excepcion.getMessage());
                conexion.rollback();
            } finally {
                conexion.setAutoCommit(true);
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al conectar con la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Estudiante consultar(int id) {
        
        String sql = "SELECT e.id, e.codigo, e.programa, e.promedio, p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id " +
                 "FROM Prueba.Estudiante e " +
                 "JOIN Prueba.Persona p ON e.persona_id = p.id " +
                 "WHERE e.id = ?"; 

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {
                if (resultados.next()) {
                    
                    int estudianteId = resultados.getInt("id");
                    String codigo = resultados.getString("codigo");
                    String programa = resultados.getString("programa");
                    double promedio = resultados.getDouble("promedio");
                    int personaId = resultados.getInt("persona_id");
                    
                    Persona persona = personaDAO.consultar(personaId);
                    return estudianteFabrica.crearEstudiante(estudianteId, codigo, programa, promedio, persona);
                     
                } else {
                    return null;
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar el estudiante: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Estudiante> listar() {
        
        String sql = "SELECT e.id, e.codigo, e.programa, e.promedio, " +
                     "p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id " +
                     "FROM Prueba.Estudiante e " +
                     "JOIN Prueba.Persona p ON e.persona_id = p.id";

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {

            while (resultados.next()) {
                
                int id = resultados.getInt("id");
                String codigo = resultados.getString("codigo");
                String programa = resultados.getString("programa");
                double promedio = resultados.getDouble("promedio");
                int personaId = resultados.getInt("persona_id");
                
                Persona persona = personaDAO.consultar(personaId);
                estudiantes.add(estudianteFabrica.crearEstudiante(id, codigo, programa, promedio, persona));
            }
            
            for(Estudiante estudiante : estudiantes){
                System.out.println(estudiante.toString());
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar los estudiantes: " + excepcion.getMessage());
        }
        
        return estudiantes;
    }

    @Override
    public void actualizar(Estudiante estudiante) {
        
        String sql = "UPDATE Prueba.Estudiante SET codigo = ?, programa = ?, promedio = ?, persona_id = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, estudiante.getCodigo());
            consulta.setString(2, estudiante.getPrograma());
            consulta.setDouble(3, estudiante.getPromedio());
            consulta.setInt(4, estudiante.getPersona()); 
            consulta.setInt(5, estudiante.getId());

            int filasAfectadas = consulta.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante actualizado con exito.");
            } else {
                System.out.println("No se encontro el estudiante para actualizar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al editar el estudiante: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        
        String sql = "DELETE FROM Prueba.Estudiante WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id); 

            int filasAfectadas = consulta.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante eliminado con exito.");
            } else {
                System.out.println("No se encontro el estudiante para eliminar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar el estudiante: " + excepcion.getMessage());
        }
    }


//    @Override
//    public void guardarEnArchivoBIN(Estudiante estudiante) {
//        
//        String ruta = "data/estudiante.bin";
//        List<Estudiante> estudiantes = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                estudiantes = (List<Estudiante>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        estudiantes.add(estudiante);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(estudiantes);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/estudiante.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Estudiante> estudiantes = (List<Estudiante>) objIn.readObject();
//
//            for (Estudiante estudiante : estudiantes) {
//                System.out.println(estudiante);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }
    
}
