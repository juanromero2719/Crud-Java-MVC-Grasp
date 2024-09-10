package Vista;

import Servicio.CargoServicio;
import Usuario.Cargo;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaCargo {

    private CargoServicio cargoServicio;
    private Scanner scanner;
    private final Validador validador;

    public VistaCargo() {
        this.cargoServicio = new CargoServicio();
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
                System.out.println("Opción no válida.");
        }
    }

    private void agregarCargo() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        String nombre = validador.obtenerStringValido("Ingrese el nombre del cargo (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        cargoServicio.agregarCargo(id, nombre);
    }

    private void consultarCargo() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        Cargo cargo = cargoServicio.obtenerCargo(id);
        if (cargo != null) {
            System.out.println("Nombre: " + cargo.getNombre());
        } else {
            System.out.println("No se encontró un cargo con el ID proporcionado.");
        }
    }

    private void listarCargos() {
        cargoServicio.listarCargos();
    }

    private void actualizarCargo() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo a actualizar (solo números enteros positivos): ");
        String nombre = validador.obtenerStringValido("Ingrese el nuevo nombre del cargo (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        cargoServicio.actualizarCargo(id, nombre);
    }

    private void eliminarCargo() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo a eliminar (solo números enteros positivos): ");
        cargoServicio.eliminarCargo(id);
    }

    private void guardarCSV() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        Cargo cargo = cargoServicio.obtenerCargo(id);
        if (cargo != null) {
            cargoServicio.guardarEnArchivoCSV(cargo);
            System.out.println("Cargo guardado en archivo CSV con éxito.");
        } else {
            System.out.println("Cargo no encontrado.");
        }
    }

    private void leerCSV() {
        cargoServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        int id = validador.obtenerIdValido("Ingrese el ID del cargo: ");
        Cargo cargo = cargoServicio.obtenerCargo(id);
        if (cargo != null) {
            cargoServicio.guardarEnArchivoBIN(cargo);
            System.out.println("Cargo guardado en archivo BIN con éxito.");
        } else {
            System.out.println("Cargo no encontrado.");
        }
    }

    private void leerBIN() {
        cargoServicio.leerDesdeArchivoBIN();
    }


}
