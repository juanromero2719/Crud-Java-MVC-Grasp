package Vista;

import Controlador.EmpleadoController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaEmpleado {

    private final EmpleadoController empleadoController;
    private final Scanner scanner;
    private final Validador validador;

    public VistaEmpleado() {
        this.empleadoController = new EmpleadoController();
        this.scanner = new Scanner(System.in);
        this.validador = new Validador();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = obtenerOpcionMenu();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        int opcion = -1;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de empleado:");
                System.out.println("1. Agregar empleado");
                System.out.println("2. Consultar empleado");
                System.out.println("3. Listar empleado");
                System.out.println("4. Actualizar empleado");
                System.out.println("5. Eliminar empleados");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion < 0 || opcion > 9) {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                } else {
                    inputValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero.");
                scanner.next(); 
            }
        }
        return opcion;
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarEmpleado();
                break;
            case 2:
                consultarEmpleado();
                break;
            case 3:
                listarEmpleados();
                break;
            case 4:
                actualizarEmpleado();
                break;
            case 5:
                eliminarEmpleado();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opcion invalida. Intente nuevamente.");
        }
    }

    private void agregarEmpleado() {
        int idEmpleado = validador.obtenerIdValido("Ingrese el ID del empleado (solo numeros enteros positivos): ");
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona (solo numeros enteros positivos): ");
        int idCargo = validador.obtenerIdValido("Ingrese el ID del cargo (solo numeros enteros positivos): ");
        double salario = validador.obtenerDoubleValido("Ingrese el salario (solo numeros): ");
        empleadoController.agregarEmpleado(idEmpleado, idCargo, salario, idPersona);
    }

    private void consultarEmpleado() {
        
        int idEmpleado = validador.obtenerIdValido("Ingrese el ID del empleado (solo números enteros positivos): ");
        empleadoController.consultarEmpleado(idEmpleado);

    }

    private void actualizarEmpleado() {
        
        int idEmpleado = validador.obtenerIdValido("Ingrese el ID del empleado: ");            
        double salario = validador.obtenerDoubleValido("Ingrese el nuevo salario: ");
        int idCargo = validador.obtenerIdValido("Ingrese el nuevo ID del cargo: ");
        int idPersona = validador.obtenerIdValido("Ingrese el nuevo ID de la persona: ");          
        empleadoController.actualizarEmpleado(idEmpleado, idCargo, salario, idPersona);
       
    }

    private void eliminarEmpleado() {
        
        int idEmpleado = validador.obtenerIdValido("Ingrese el ID del empleado: ");
        empleadoController.eliminarEmpleado(idEmpleado);
        
    }

    private void listarEmpleados() {
        empleadoController.listarEmpleados();
    }
  
}
