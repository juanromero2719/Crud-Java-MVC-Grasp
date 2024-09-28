package Vista;

import Controlador.CargoController;

import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaCargo {

    private CargoController cargoController;
    private Scanner scanner;
    private final Validador validador;

    public VistaCargo() {
        
        this.cargoController = new CargoController();
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
                System.out.println("Menu CRUD de Cargo:");
                System.out.println("1. Agregar Cargo");
                System.out.println("2. Consultar Cargo");
                System.out.println("3. Listar Cargos");
                System.out.println("4. Actualizar Cargo");
                System.out.println("5. Eliminar Cargo");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion < 0 || opcion > 9) {
                    System.out.println("Opcion no válida. Intente nuevamente.");
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
                agregarCargo();
                break;
            case 2:
                consultarCargo();
                break;
            case 3:
                listarCargos();
                break;
            case 4:
                actualizarCargo();
                break;
            case 5:
                eliminarCargo();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void agregarCargo() {
        
        int idCargo = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        String nombreCargo = validador.obtenerStringValido("Ingrese el nombre del cargo (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        cargoController.agregarCargo(idCargo, nombreCargo);
    }

    private void consultarCargo() {
        
        int idCargo = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        cargoController.obtenerCargo(idCargo);
          
    }

    private void listarCargos() {
        cargoController.listarCargos();
    }

    private void actualizarCargo() {
        
        int idCargo = validador.obtenerIdValido("Ingrese el ID del cargo a actualizar (solo numeros enteros positivos): ");
        String nombreCargo = validador.obtenerStringValido("Ingrese el nuevo nombre del cargo (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        cargoController.actualizarCargo(idCargo, nombreCargo);
        
    }

    private void eliminarCargo() {
        int idCargo = validador.obtenerIdValido("Ingrese el ID del cargo a eliminar (solo numeros enteros positivos): ");
        cargoController.eliminarCargo(idCargo);
    }

}
