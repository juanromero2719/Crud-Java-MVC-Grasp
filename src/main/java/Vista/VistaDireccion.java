package Vista;

import Controlador.DireccionController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaDireccion {

    private final DireccionController direccionController;
    private final Validador validador;
    private final Scanner scanner;

    public VistaDireccion() {
        this.direccionController = new DireccionController();
        this.validador = new Validador();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = obtenerOpcionMenu();
            switch (opcion) {
                case 1:
                    agregarDireccion();
                    break;
                case 2:
                    consultarDireccion();
                    break;
                case 3:
                    listarDirecciones();
                    break;
                case 4:
                    actualizarDireccion();
                    break;
                case 5:
                    eliminarDireccion();
                    break;
                case 10:
                    obtenerDireccionCompleta();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de direcciones:");
                System.out.println("1. Agregar direccion");
                System.out.println("2. Consultar direccion");
                System.out.println("3. Listar direccion");
                System.out.println("4. Actualizar direccion");
                System.out.println("5. Eliminar direcciones");
                System.out.println("10. Obtener direccion completa");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                if (opcion >= 0 && opcion <= 10) {
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

    private void agregarDireccion() {
        
        int idDireccion = validador.obtenerIdValido("Ingrese el ID de la direccion (solo números enteros positivos): ");
        int idMunicipio = validador.obtenerIdValido("Ingrese el ID del municipio (solo numeros enteros positivos): ");
        String calle = validador.obtenerStringValido("Ingrese la calle (solo caracteres alfanumericos): ", "[a-zA-Z0-9 ]+");
        String carrera = validador.obtenerStringValido("Ingrese la carrera (solo caracteres alfanumericos): ", "[a-zA-Z0-9 ]+");
        String coordenada = validador.obtenerStringValido("Ingrese la coordenada (solo numeros): ", "\\d+");
        String descripcion = validador.obtenerStringValido("Ingrese la descripcion (solo caracteres alfanumericos): ", "[a-zA-Z0-9 ]+");
        direccionController.agregarDireccion(idDireccion, idMunicipio, calle, carrera, coordenada, descripcion);

    }

    private void consultarDireccion() {
        
        int idDireccion = validador.obtenerIdValido("Ingrese el id de la direccion: ");
        direccionController.consultarDireccion(idDireccion);

    }

    private void actualizarDireccion() {
        
            
         int idDireccion = validador.obtenerIdValido("Ingrese el id de la direccion a actualizar (solo numeros enteros positivos): ");
        int idMunicipio = validador.obtenerIdValido("Ingrese el nuevo id del municipio (solo numeros enteros positivos): ");
        String calle = validador.obtenerStringValido("Ingrese la nueva calle (solo caracteres alfanumericos): ", "[a-zA-Z0-9 ]+");
        String carrera = validador.obtenerStringValido("Ingrese la nueva carrera (solo caracteres alfanumericos): ", "[a-zA-Z0-9 ]+");
        String coordenada = validador.obtenerStringValido("Ingrese la nueva coordenada (solo numeros): ", "\\d+");
        String descripcion = validador.obtenerStringValido("Ingrese la nueva descripcion (solo caracteres alfabeticos): ", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        direccionController.actualizarDireccion(idDireccion, idMunicipio, calle, carrera, coordenada, descripcion);

       
    }

    private void eliminarDireccion() {
        
        int idDireccion = validador.obtenerIdValido("Ingrese el id de la direccion a eliminar (solo numeros enteros positivos): ");
        direccionController.eliminarDireccion(idDireccion);

    }   

    private void listarDirecciones() {
        direccionController.listarDirecciones();
    }

    private void obtenerDireccionCompleta() {
        int idDireccion = validador.obtenerIdValido("Ingrese el id de la direccion: ");
        String direccionCompleta = direccionController.obtenerDireccionCompleta(idDireccion);
        System.out.println("Direccion completa: " + direccionCompleta);
    }
}
