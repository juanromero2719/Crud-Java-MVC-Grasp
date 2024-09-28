    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorio.H2;

import Conexion.Conexion;
import Fabrica.ConexionFabrica;
import Fabrica.MunicipioFabrica;
import Interface.IGestorDatos;
import Ubicacion.Departamento;
import Ubicacion.Municipio;
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
public class MunicipioDAOH2 implements IGestorDatos<Municipio> {
    
    private final IGestorDatos<Departamento> departamentoDAO;
    private final MunicipioFabrica municipioFabrica;
    
    public MunicipioDAOH2(){
        this.departamentoDAO = ConexionFabrica.getGestorDatos(Departamento.class);
        this.municipioFabrica = new MunicipioFabrica();
    }
    
    @Override
    public void crear(Municipio municipio) {

        String sql = "INSERT INTO Prueba.MUNICIPIO (id, nombre, departamento_id) VALUES (?, ?, ?)";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setInt(1, municipio.getId());
                consulta.setString(2, municipio.getNombre());
                consulta.setInt(3, municipio.getDepartamento().getId());
                
                consulta.executeUpdate();
                conexion.commit();
                System.out.println("Municipio agregado con exito");

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
    public Municipio consultar(int id) {
        
        String sql = "SELECT m.id AS municipio_id, m.nombre AS municipio_nombre, " +
                 "d.id AS departamento_id, d.nombre AS departamento_nombre, " +
                 "p.id AS pais_id, p.nombre AS pais_nombre " +
                 "FROM Prueba.Municipio m " +
                 "JOIN Prueba.Departamento d ON m.departamento_id = d.id " +
                 "JOIN Prueba.Pais p ON d.pais_id = p.id " +
                 "WHERE m.id = ?"; 

        try (Connection conexion = Conexion.conectar();
            PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {

                if (resultados.next()) {
                    
                    int MunicipioId = resultados.getInt("id");
                    String municipioNombre = resultados.getString("nombre");                  
                    int departamentoId = resultados.getInt("departamento_id");
                    
                    Departamento departamento = departamentoDAO.consultar(departamentoId);
                    return municipioFabrica.crearMunicipio(MunicipioId, municipioNombre, departamento);

                } 

                return null;
                
            }

        } catch (SQLException excepcion) {
          System.err.println("Error al buscar el municipio en la base de datos: " + excepcion.getMessage());
          return null;
        }
    }

    @Override
    public List<Municipio> listar() {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT m.id AS municipio_id, m.nombre AS municipio_nombre, " +
                   "d.id AS departamento_id, d.nombre AS departamento_nombre, " +
                   "p.id AS pais_id, p.nombre AS pais_nombre " +
                   "FROM Prueba.MUNICIPIO m " +
                   "JOIN Prueba.DEPARTAMENTO d ON m.departamento_id = d.id " +
                   "JOIN Prueba.PAIS p ON d.pais_id = p.id";

        try (Connection conexion = Conexion.conectar();
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                int municipioId = resultado.getInt("municipio_id");
                String municipioNombre = resultado.getString("municipio_nombre");
                int departamentoId = resultado.getInt("departamento_id");
               
                Departamento departamento = departamentoDAO.consultar(departamentoId);
                municipios.add(municipioFabrica.crearMunicipio(municipioId, municipioNombre, departamento));
            }
            
            for(Municipio municipio : municipios){
                System.out.println(municipio.toString());
            }

        } catch (SQLException excepcion) {
          System.err.println("Error al listar los municipios: " + excepcion.getMessage());
        }
        
        return municipios;

    }

    @Override
    public void actualizar(Municipio municipio) {
        String sql = "UPDATE Prueba.MUNICIPIO SET nombre = ?, departamento_id = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, municipio.getNombre());
            consulta.setInt(2, municipio.getDepartamento().getId());
            consulta.setInt(3, municipio.getId());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El municipio ha sido actualizado con éxito.");
            } else {
                System.out.println("No se encontró un municipio con el ID proporcionado.");
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al editar el municipio en la base de datos: " + excepcion.getMessage());
        }  
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Prueba.MUNICIPIO WHERE id = ?";

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
//    public void guardarEnArchivoCSV(Municipio municipio) {
//        
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV, true))) {
//            writer.write(municipio.getId() + ", " + municipio.getNombre() + ", " + municipio.getDepartamento().getNombre());
//            writer.newLine();          
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar en archivo CSV: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoCSV() {
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
//    public void guardarEnArchivoBIN(Municipio municipio) {
//        
//        String ruta = "data/municipio.bin";
//        List<Municipio> municipios = new ArrayList<>();
//
//        File archivo = new File(ruta);
//        if (archivo.exists()) {
//            try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//                
//                municipios = (List<Municipio>) objIn.readObject();
//                
//            } catch (IOException | ClassNotFoundException excepcion) {
//                System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//            }
//        }
//        
//        municipios.add(municipio);
//        
//        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(ruta))) {
//            objOut.writeObject(municipios);
//            
//        } catch (IOException excepcion) {
//            System.err.println("Error al guardar el archivo binario: " + excepcion.getMessage());
//        }
//    }
//
//    @Override
//    public void leerDesdeArchivoBIN() {
//        
//        String ruta = "data/municipio.bin";
//
//        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(ruta))) {
//            List<Municipio> municipios = (List<Municipio>) objIn.readObject();
//
//            for (Municipio municipio : municipios) {
//                System.out.println(municipio);
//            }
//
//        } catch (IOException | ClassNotFoundException excepcion) {
//            System.err.println("Error al leer el archivo binario: " + excepcion.getMessage());
//        }
//    }
    
}
