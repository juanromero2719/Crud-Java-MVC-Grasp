package Vista;


import Controlador.DepartamentoController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaDepartamento {

    private final DepartamentoController departamentoController;
    private final Scanner scanner;
    private final Validador validador;

    public VistaDepartamento() {
        this.departamentoController = new DepartamentoController();
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
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de Departamento:");
                System.out.println("1. Agregar Departamento");
                System.out.println("2. Consultar Departamento");
                System.out.println("3. Listar Departamentos");
                System.out.println("4. Actualizar Departamento");
                System.out.println("5. Eliminar Departamento");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion >= 0 && opcion <= 9) {
                    return opcion;
                } else {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero.");
                scanner.next(); 
            }
        }
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarDepartamento();
                break;
            case 2:
                consultarDepartamento();
                break;
            case 3:
                listarDepartamentos();
                break;
            case 4:
                actualizarDepartamento();
                break;
            case 5:
                eliminarDepartamento();
                break;       
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción invalida. Intente nuevamente.");
        }
    }

    private void agregarDepartamento() {
        
        int idDepartamento = validador.obtenerIdValido("Ingrese el ID del Departamento: ");
        String nombreDepartamento = validador.obtenerStringValido("Ingrese el nombre del departamento (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        int idPais = validador.obtenerIdValido("Ingrese el ID del pais (solo numeros enteros positivos): ");       
        departamentoController.agregarDepartamento(idDepartamento, nombreDepartamento, idPais);
        
    }

    private void consultarDepartamento() {
        
        int idDepartamento = validador.obtenerIdValido("Ingrese el ID del departamento (solo numeros enteros positivos): ");
        departamentoController.consultarDepartamento(idDepartamento);
               
    }

    private void actualizarDepartamento() {
        int idDepartamento = validador.obtenerIdValido("Ingrese el ID del departamento a actualizar (solo numeros enteros positivos): ");
        String nombreDepartamento = validador.obtenerStringValido("Ingrese el nuevo nombre del departamento (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        int idPais = validador.obtenerIdValido("Ingrese el nuevo ID del pais (solo numeros enteros positivos): ");       
        departamentoController.actualizarDepartamento(idDepartamento, nombreDepartamento, idPais);
           
        
    }

    private void eliminarDepartamento() {
        int idDepartamento = validador.obtenerIdValido("Ingrese el ID del departamento a eliminar (solo numeros enteros positivos): ");
        departamentoController.eliminarDepartamento(idDepartamento);
    }

    private void listarDepartamentos() {
        departamentoController.listarDepartamentos();
    }
  
}
