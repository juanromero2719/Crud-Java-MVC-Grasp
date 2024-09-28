/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Interface.IConexion;
import java.sql.Connection;

/**
 *
 * @author wrydmoon
 */
public class CsvBd implements IConexion{

//    private static final String FILE_PATH = "datos.csv";
    
    @Override
    public Connection conectar() {
        System.out.println("Preparando conexión a CSV...");
        return null;
    }
    
}
