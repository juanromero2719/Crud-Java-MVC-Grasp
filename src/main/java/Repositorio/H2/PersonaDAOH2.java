/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.ConexionFabrica;
import Fabrica.PersonaFabrica;
import Interface.IGestorDatos;
import Ubicacion.Direccion;
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
public class PersonaDAOH2 implements IGestorDatos<Persona>{
    
    private final IGestorDatos<Direccion> direccionDAO;
    private final PersonaFabrica personaFabrica;
    
    public PersonaDAOH2(){
        this.direccionDAO = ConexionFabrica.getGestorDatos(Direccion.class);
        this.personaFabrica = new PersonaFabrica(); 
    }
    
    @Override
    public void crear(Persona persona) {

        String sql = "INSERT INTO Prueba.PERSONA (id, nombres, apellidos, direccion_id) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, persona.getId());
            consulta.setString(2, persona.getNombres());
            consulta.setString(3, persona.getApellidos());
            consulta.setInt(4, persona.getDireccion().getId());
            consulta.executeUpdate();
            System.out.println("Persona creada con exito.");

        } catch (SQLException excepcion) {
            System.err.println("Error al insertar la persona en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public Persona consultar(int id) {
        String sqlPersona = "SELECT p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id " +
                        "FROM Prueba.PERSONA p " +
                        "WHERE p.id = ?";


        try (Connection conexion = Conexion.conectar();
            PreparedStatement consultaPersona = conexion.prepareStatement(sqlPersona)) {

            consultaPersona.setInt(1, id);

            try (ResultSet resultadosPersona = consultaPersona.executeQuery()) {
                
                if (resultadosPersona.next()) {

                    int personaId = resultadosPersona.getInt("persona_id");
                    String nombre = resultadosPersona.getString("nombres");
                    String apellido = resultadosPersona.getString("apellidos");                  
                    int direccionId = resultadosPersona.getInt("direccion_id");
                    
                    Direccion direccion = direccionDAO.consultar(direccionId);
                    return personaFabrica.crearPersona(personaId, nombre, apellido, direccion); 
               
                } else {
                    return null;
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar la persona en la base de datos: " + excepcion.getMessage());
            return null;
        }
    }

    @Override
    public List<Persona> listar() {
        
        List<Persona> personas = new ArrayList<>();

        String sqlPersona = "SELECT p.id AS persona_id, p.nombres, p.apellidos, p.direccion_id " +
                            "FROM Prueba.PERSONA p";

        String sqlDireccion = "SELECT d.id AS direccion_id, d.calle, d.carrera, d.coordenada, d.descripcion, " +
                              "m.id AS municipio_id, m.nombre AS municipio_nombre, " +
                              "de.id AS departamento_id, de.nombre AS departamento_nombre, " +
                              "p.id AS pais_id, p.nombre AS pais_nombre " +
                              "FROM Prueba.DIRECCION d " +
                              "JOIN Prueba.MUNICIPIO m ON d.municipio_id = m.id " +
                              "JOIN Prueba.DEPARTAMENTO de ON m.departamento_id = de.id " +
                              "JOIN Prueba.PAIS p ON de.pais_id = p.id " +
                              "WHERE d.id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consultaPersona = conexion.prepareStatement(sqlPersona)) {

            try (ResultSet resultadosPersona = consultaPersona.executeQuery()) {
                while (resultadosPersona.next()) {
                    int personaId = resultadosPersona.getInt("persona_id");
                    String nombres = resultadosPersona.getString("nombres");
                    String apellidos = resultadosPersona.getString("apellidos");
                    int direccionId = resultadosPersona.getInt("direccion_id");

                    try (PreparedStatement consultaDireccion = conexion.prepareStatement(sqlDireccion)) {
                        consultaDireccion.setInt(1, direccionId);

                        try (ResultSet resultadosDireccion = consultaDireccion.executeQuery()) {
                            if (resultadosDireccion.next()) {
                                
                                Direccion direccion = direccionDAO.consultar(direccionId);
                                personas.add(personaFabrica.crearPersona(personaId, nombres, apellidos, direccion));
                            }
                                                                           
                        }
                    }
                }
                for (Persona persona : personas) {
                    System.out.println(persona.informacion());
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al listar personas en la base de datos: " + excepcion.getMessage());
        }
        
        return personas;

    }

    @Override
    public void actualizar(Persona persona) {
        
        String sql = "UPDATE Prueba.PERSONA SET nombres = ?, apellidos = ?, direccion_id = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, persona.getNombres());
            consulta.setString(2, persona.getApellidos());
            consulta.setInt(3, persona.getDireccion().getId());
            consulta.setInt(4, persona.getId());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Persona con ID " + persona.getId() + " fue actualizada correctamente.");
            } else {
                System.out.println("No se encontro la persona con ID " + persona.getId() + " para actualizar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al actualizar la persona en la base de datos: " + excepcion.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        
        String sql = "DELETE FROM Prueba.PERSONA WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Persona con ID: " + id + " fue eliminada correctamente.");
            } else {
                System.out.println("No se encontro la persona con ID: " + id + " para eliminar.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al borrar la persona de la base de datos: " + excepcion.getMessage());
        }
    }

//    @Override
//    public void guardarEnArchivoCSV(Persona persona) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
//            writer.write(persona.getId() + ", " + persona.getNombres() + ", " + persona.getApellidos() + ", " + persona.getDireccion().getId());
//            writer.newLine();          
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
//        }
//    }
//    
//    @Override
//    public void leerDesdeArchivoCSV() {
//        
//        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
//            String linea;
//            while ((linea = reader.readLine()) != null) {
//                System.out.println(linea);
//            }
//            System.out.println("Datos leidos del archivo " + ARCHIVO_CSV);
//        } catch (IOException excepcion) {
//            System.err.println("Error al leer desde archivo CSV: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void guardarEnArchivoBIN(Persona persona) {
//        
//        String ruta = "data/persona.bin";
//        List<Persona> personas = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                personas = (List<Persona>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        personas.add(persona);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(personas);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/persona.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Persona> personas = (List<Persona>) objIn.readObject();
//
//            for (Persona persona : personas) {
//                System.out.println(persona);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }
    
}
