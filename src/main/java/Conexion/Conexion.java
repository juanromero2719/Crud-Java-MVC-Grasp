/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;


import static com.mycompany.ejercicio1.Ejercicio1.crud_mvc;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author wrydmoon
 */


public class Conexion {
    
    private static Connection conexion;
    
    public static Connection conectar(){
           
       try{
           
           Class.forName("org.h2.Driver");
           conexion = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
           return conexion;

           
       }catch(Exception excepcion){
           System.out.println("Error: " + excepcion);
           return null;
       }
    }
    
    public static Connection getConexion(){
        return conexion;
    }

}
