package Vista;

import Controlador.PaisController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaPais {

    private PaisController paisController;
    private Scanner scanner;
    private final Validador validador;
    
    public VistaPais() {
        this.paisController = new PaisController();
        this.scanner = new Scanner(System.in);
        this.validador = new Validador();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = obtenerOpcionMenu();
            switch (opcion) {
                case 1:
                    agregarPais();
                    break;
                case 2:
                    consultarPais();
                    break;
                case 3:
                    listarPaises();
                    break;
                case 4:
                    actualizarPais();
                    break;
                case 5:
                    eliminarPais();
                    break;               
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no válida.");
            }
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        int opcion = -1;
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de Pais:");
                System.out.println("1. Agregar Pais");
                System.out.println("2. Consultar Pais");
                System.out.println("3. Listar Paises");
                System.out.println("4. Actualizar Pais");
                System.out.println("5. Eliminar Pais");               
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion < 0 || opcion > 9) {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                } else {
                    return opcion;
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero.");
                scanner.next();
            }
        }
    }

    private void agregarPais() {
        
        int idPais = validador.obtenerIdValido("Ingrese el ID del pais (solo numeros enteros positivos): ");
        String nombrePais = validador.obtenerStringValido("Ingrese el nombre del pais (solo caracteres alfabeticos y al menos 3 caracteres): ", "[a-zA-Z0-9 ]+");        
        paisController.agregarPais(idPais, nombrePais);
    }

    private void consultarPais() {
        
        int idPais = validador.obtenerIdValido("Ingrese el ID del pais (solo numeros): ");      
        paisController.obtenerPais(idPais);
        
    }

    private void actualizarPais() {
        int idPais = validador.obtenerIdValido("Ingrese el ID del pais a actualizar (solo numeros): ");
        String nombrePais = validador.obtenerStringValido("Ingrese el nuevo nombre del pais (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");      
        paisController.actualizarPais(idPais, nombrePais);

    }

    private void eliminarPais() {
        
        int idPais = validador.obtenerIdValido("Ingrese el ID del pais a eliminar (solo numeros): ");        
        paisController.eliminarPais(idPais);

    }

    private void listarPaises() {
        paisController.listarPaises();
    }

}
