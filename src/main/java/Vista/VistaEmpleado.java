package Vista;

import Controlador.CargoController;
import Controlador.EmpleadoController;
import Controlador.PersonaController;
import Servicio.EmpleadoServicio;
import Usuario.Cargo;
import Usuario.Empleado;
import Usuario.Persona;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaEmpleado {

    private final EmpleadoServicio empleadoServicio;
    private final Scanner scanner;
    private final Validador validador;

    public VistaEmpleado() {
        this.empleadoServicio = new EmpleadoServicio();
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
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV ");
                System.out.println("8. Guardar BIN");
                System.out.println("9. Leer BIN");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion < 0 || opcion > 9) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                } else {
                    inputValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
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
            case 6:
                guardarCSV();
                break;
            case 7:
                leerCSV();
                break;
            case 8:
                guardarBIN();
                break;
            case 9:
                leerBIN();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private void agregarEmpleado() {
        int empleadoId = validador.obtenerIdValido("Ingrese el ID del empleado (solo números enteros positivos): ");
        int personaId = validador.obtenerIdValido("Ingrese el ID de la persona (solo números enteros positivos): ");
        int cargoId = validador.obtenerIdValido("Ingrese el ID del cargo (solo números enteros positivos): ");
        double salario = validador.obtenerDoubleValido("Ingrese el salario (solo números): ");

        empleadoServicio.agregarEmpleado(empleadoId, cargoId, salario, personaId);
    }

    private void consultarEmpleado() {
        
        int id = validador.obtenerIdValido("Ingrese el ID del empleado (solo números enteros positivos): ");
        Empleado empleado = empleadoServicio.consultarEmpleado(id);
        System.out.println(empleado != null ? empleado.toString() : "Empleado no encontrado.");
    }

    private void actualizarEmpleado() {
        
        int id = validador.obtenerIdValido("Ingrese el ID del empleado: ");
        Empleado empleado = empleadoServicio.consultarEmpleado(id);

        if (empleado != null) {
            
            double salario = validador.obtenerDoubleValido("Ingrese el nuevo salario: ");
            int cargoId = validador.obtenerIdValido("Ingrese el nuevo ID del cargo: ");
            int personaId = validador.obtenerIdValido("Ingrese el nuevo ID de la persona: ");
          
            empleadoServicio.actualizarEmpleado(id, cargoId, salario, personaId);
            
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private void eliminarEmpleado() {
        
        int id = validador.obtenerIdValido("Ingrese el ID del empleado: ");
        empleadoServicio.eliminarEmpleado(id);
        System.out.println("Empleado eliminado con éxito.");
    }

    private void listarEmpleados() {
        empleadoServicio.listarEmpleados();
    }

    private void guardarCSV() {
        guardarArchivo("CSV");
    }

    private void leerCSV() {
        empleadoServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        guardarArchivo("BIN");
    }

    private void leerBIN() {
        empleadoServicio.leerDesdeArchivoBIN();
    }

    private void guardarArchivo(String tipoArchivo) {
        int id = validador.obtenerIdValido("Ingrese el ID del empleado: ");
    
        if (tipoArchivo.equals("CSV")) {
            
            empleadoServicio.guardarEnArchivoCSV(id);
            System.out.println("Empleado guardado en archivo CSV con éxito.");
        } else if (tipoArchivo.equals("BIN")) {
            
            empleadoServicio.guardarEnArchivoBIN(id);
            System.out.println("Empleado guardado en archivo BIN con éxito.");
        }
        
    }


   
}
