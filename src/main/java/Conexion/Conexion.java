/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;


import Repositorio.CSV.PaisDAOCSV;
import Repositorio.CSV.CargoDAOCSV;
import Repositorio.CSV.PersonaDAOCSV;
import Repositorio.CSV.EstudianteDAOCSV;
import Repositorio.CSV.DepartamentoDAOCSV;
import Repositorio.CSV.EmpleadoDAOCSV;
import Repositorio.CSV.MunicipioDAOCSV;
import Repositorio.CSV.DireccionDAOCSV;
import Repositorio.H2.CargoDAOH2;
import Repositorio.H2.EmpleadoDAOH2;
import Repositorio.H2.PersonaDAOH2;
import Repositorio.H2.EstudianteDAOH2;
import Repositorio.H2.MunicipioDAOH2;
import Repositorio.H2.DireccionDAOH2;
import Repositorio.H2.DepartamentoDAOH2;
import Repositorio.H2.PaisDAOH2;
import Interface.IConexion;
import java.sql.Connection;
import Ubicacion.*;
import Fabrica.ConexionFabrica;
import Usuario.*;
/**
 *
 * @author wrydmoon
 */

public class Conexion {
    
    private static IConexion conexion;
    
    private static void configurarDAOH2() {
        ConexionFabrica.setGestorDatos(Cargo.class, new CargoDAOH2());
        ConexionFabrica.setGestorDatos(Pais.class, new PaisDAOH2());
        ConexionFabrica.setGestorDatos(Departamento.class, new DepartamentoDAOH2());
        ConexionFabrica.setGestorDatos(Municipio.class, new MunicipioDAOH2());
        ConexionFabrica.setGestorDatos(Direccion.class, new DireccionDAOH2());
        ConexionFabrica.setGestorDatos(Persona.class, new PersonaDAOH2());
        ConexionFabrica.setGestorDatos(Empleado.class, new EmpleadoDAOH2());
        ConexionFabrica.setGestorDatos(Estudiante.class, new EstudianteDAOH2());
        
    }
    
    private static void configurarDAOCSV() {
        ConexionFabrica.setGestorDatos(Cargo.class, new CargoDAOCSV());
        ConexionFabrica.setGestorDatos(Pais.class, new PaisDAOCSV());
        ConexionFabrica.setGestorDatos(Departamento.class, new DepartamentoDAOCSV());
        ConexionFabrica.setGestorDatos(Municipio.class, new MunicipioDAOCSV());
        ConexionFabrica.setGestorDatos(Direccion.class, new DireccionDAOCSV());
        ConexionFabrica.setGestorDatos(Persona.class, new PersonaDAOCSV());
        ConexionFabrica.setGestorDatos(Empleado.class, new EmpleadoDAOCSV());
        ConexionFabrica.setGestorDatos(Estudiante.class, new EstudianteDAOCSV());
    }
    
    public static void setConexion(IConexion nuevaConexion) {
        
        conexion = nuevaConexion;
        String tipoConexion = nuevaConexion.getClass().getSimpleName();
        
        switch (tipoConexion) {
            case "H2Bd":
                configurarDAOH2();
                System.out.println("Conexion configurada para usar H2.");
                break;
            case "CsvBd":
                configurarDAOCSV();
                System.out.println("Conexion configurada para usar CSV.");
                break;
            default:
                throw new IllegalArgumentException("Tipo de conexion no soportado: " + tipoConexion);
        }
        
    }

    public static Connection conectar() {
        if (conexion == null) {
            throw new IllegalStateException("No se ha seleccionado ninguna base de datos.");
        }
        return conexion.conectar();
    }
    
    

    
    

}
