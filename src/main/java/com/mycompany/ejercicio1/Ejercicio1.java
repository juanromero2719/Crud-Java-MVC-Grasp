/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ejercicio1;

import Vista.VistaCargo;
import Vista.VistaConexion;
import Vista.VistaDepartamento;
import Vista.VistaDireccion;
import Vista.VistaEmpleado;
import Vista.VistaEstudiante;
import Vista.VistaMunicipio;
import Vista.VistaPais;
import Vista.VistaPersona;
import VistaGrafica.ConexionGUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio1 {

    public static void crud_mvc() {
        Scanner scanner = new Scanner(System.in);
        VistaCargo vistaCargo = new VistaCargo();
        VistaPais vistaPais = new VistaPais();
        VistaDepartamento vistaDepartamento = new VistaDepartamento();
        VistaMunicipio vistaMunicipio = new VistaMunicipio();
        VistaDireccion vistaDireccion = new VistaDireccion();
        VistaPersona vistaPersona = new VistaPersona();
        VistaEmpleado vistaEmpleado = new VistaEmpleado();
        VistaEstudiante vistaEstudiante = new VistaEstudiante();

        int opcion = -1;
        do {
            boolean inputValido = false; 
            while (!inputValido) {
                try {
                    System.out.println("\n--- Menu Principal ---");
                    System.out.println("1. Gestionar Cargos");
                    System.out.println("2. Gestionar Paises");
                    System.out.println("3. Gestionar Departamentos");
                    System.out.println("4. Gestionar Municipios");
                    System.out.println("5. Gestionar Direcciones");
                    System.out.println("6. Gestionar Personas");
                    System.out.println("7. Gestionar Empleados");
                    System.out.println("8. Gestionar Estudiantes");
                    System.out.println("0. Salir");
                    System.out.print("Seleccione una opcion: ");
                    opcion = scanner.nextInt();

                    if (opcion < 0 || opcion > 8) {
                        System.out.println("Opcion invalida. Intente nuevamente.");
                    } else {
                        inputValido = true; 
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.next(); 
                }
            }

            switch (opcion) {
                case 1:
                    vistaCargo.mostrarMenu();
                    break;
                case 2:
                    vistaPais.mostrarMenu();
                    break;
                case 3:
                    vistaDepartamento.mostrarMenu();
                    break;
                case 4:
                    vistaMunicipio.mostrarMenu();
                    break;
                case 5:
                    vistaDireccion.mostrarMenu();
                    break;
                case 6:
                    vistaPersona.mostrarMenu();
                    break;
                case 7:
                    vistaEmpleado.mostrarMenu();
                    break;
                case 8:
                    vistaEstudiante.mostrarMenu();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
        scanner.close();
    }

    public static void main(String[] args) {
        
        
        
        Thread guiThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexionGUI vistaGUI = new ConexionGUI();
                vistaGUI.show();         
            }
        });

//        Thread consoleThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                VistaConexion vistaConexion = new VistaConexion();
//                vistaConexion.iniciar();
//
//                crud_mvc();
//            }
//        });

        guiThread.start();
//        consoleThread.start();
    }
}
