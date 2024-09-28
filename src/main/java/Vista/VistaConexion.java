/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.Conexion;
import Conexion.CsvBd;
import Conexion.H2Bd;

import java.util.Scanner;

/**
 *
 * @author wrydmoon
 */
public class VistaConexion {
       
    public void iniciar() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Seleccione la base de datos:");
        System.out.println("1. H2");
        System.out.println("2. CSV");
        System.out.print("Seleccione una opcion: ");
        int opcion = scanner.nextInt();
         
        switch (opcion) {
            case 1:
                Conexion.setConexion(new H2Bd());               
                break;
            case 2:
                Conexion.setConexion(new CsvBd());     
                break;
            default:
                System.out.println("Opcion no valida. Saliendo...");
                return; 
        }
             
    }
}
