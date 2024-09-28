package Vista;


import Controlador.PersonaController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * @author wrydmoon
 */
public class VistaPersona {

    private final PersonaController personaController;
    private final Scanner scanner;
    private final Validador validador;

    public VistaPersona() {
        this.personaController = new PersonaController();
        this.scanner = new Scanner(System.in);
        this.validador = new Validador();
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            opcion = obtenerOpcionMenu();
            switch (opcion) {
                case 1:
                    agregarPersona();
                    break;
                case 2:
                    consultarPersona();
                    break;
                case 3:
                    listarPersonas();
                    
                    break;
                case 4:
                    actualizarPersona();
                    
                    break;
                case 5:
                    eliminarPersona();
                    break;                
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de personas:");
                System.out.println("1. Agregar persona");
                System.out.println("2. Consultar persona");
                System.out.println("3. Listar persona");
                System.out.println("4. Actualizar persona");
                System.out.println("5. Eliminar personas");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
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

    private void agregarPersona() {
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona (solo numeros enteros positivos): ");
        String nombres = validador.obtenerStringValido("Ingrese los nombres de la persona (solo caracteres alfabeticos y espacios): ", "[a-zA-Z0-9 ]+");
        String apellidos = validador.obtenerStringValido("Ingrese los apellidos de la persona (solo caracteres alfabeticos y espacios): ", "[a-zA-Z0-9 ]+");
        int idDireccion = validador.obtenerIdValido("Ingrese el ID de la direccion (solo numeros enteros positivos): ");      
        personaController.agregarPersona(idPersona, nombres, apellidos, idDireccion);

    }

    private void consultarPersona() {
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona (solo numeros): ");
        personaController.consultarPersona(idPersona);
    }

    private void actualizarPersona() {
        
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona a actualizar (solo numeros): ");
        String nombres = validador.obtenerStringValido("Ingrese los nuevos nombres de la persona (solo caracteres alfabeticos y espacios): ", "[a-zA-Z0-9 ]+");
        String apellidos = validador.obtenerStringValido("Ingrese los nuevos apellidos de la persona (solo caracteres alfabeticos y espacios): ", "[a-zA-Z0-9 ]+");
        int idDireccion = validador.obtenerIdValido("Ingrese el nuevo ID de la direccion (solo números): ");
        personaController.actualizarPersona(idPersona, nombres, apellidos, idDireccion);
        
    }

    private void eliminarPersona() {
        
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona a eliminar (solo numeros): ");        
        personaController.eliminarPersona(idPersona);
           
    }

    private void listarPersonas() {
        personaController.listarPersonas();
    }

}
