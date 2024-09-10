package Vista;

import Controlador.DireccionController;
import Controlador.MunicipioController;
import Servicio.DireccionServicio;
import Ubicacion.Direccion;
import Ubicacion.Municipio;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaDireccion {

    private final DireccionServicio direccionServicio;
    private final Validador validador;
    private final Scanner scanner;

    public VistaDireccion() {
        this.direccionServicio = new DireccionServicio();
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
                case 10:
                    obtenerDireccionCompleta();
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
                System.out.println("Menu CRUD de direcciones:");
                System.out.println("1. Agregar direccion");
                System.out.println("2. Consultar direccion");
                System.out.println("3. Listar direccion");
                System.out.println("4. Actualizar direccion");
                System.out.println("5. Eliminar direcciones");
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV ");
                System.out.println("8. Guardar BIN");
                System.out.println("9. Leer BIN");
                System.out.println("10. Obtener direccion completa");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                if (opcion >= 0 && opcion <= 10) {
                    return opcion;
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.next(); 
            }
        }
    }

    private void agregarDireccion() {
        try {
            int id = validador.obtenerIdValido("Ingrese el ID de la dirección (solo números enteros positivos): ");
            int municipioId = validador.obtenerIdValido("Ingrese el ID del municipio (solo números enteros positivos): ");
            String calle = validador.obtenerStringValido("Ingrese la calle (solo caracteres alfanuméricos): ", "[a-zA-Z0-9 ]+");
            String carrera = validador.obtenerStringValido("Ingrese la carrera (solo caracteres alfanuméricos): ", "[a-zA-Z0-9 ]+");
            String coordenada = validador.obtenerStringValido("Ingrese la coordenada (solo números): ", "\\d+");
            String descripcion = validador.obtenerStringValido("Ingrese la descripción (solo caracteres alfanuméricos): ", "[a-zA-Z0-9 ]+");

            direccionServicio.agregarDireccion(id, municipioId, calle, carrera, coordenada, descripcion);
     
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Asegúrese de ingresar un número correcto para los IDs.");
            scanner.next();
        }
    }

    private void consultarDireccion() {
        
        int id = validador.obtenerIdValido("Ingrese el id de la direccion: ");
        Direccion direccion = direccionServicio.consultarDireccion(id);
        System.out.println(direccion != null ? direccion.toString() : "Direccion no encontrada.");
    }

    private void actualizarDireccion() {
        try {
            
            int id = validador.obtenerIdValido("Ingrese el id de la direccion a actualizar (solo números enteros positivos): ");
            int municipioId = validador.obtenerIdValido("Ingrese el nuevo id del municipio (solo números enteros positivos): ");
            String calle = validador.obtenerStringValido("Ingrese la nueva calle (solo caracteres alfanuméricos): ", "[a-zA-Z0-9 ]+");
            String carrera = validador.obtenerStringValido("Ingrese la nueva carrera (solo caracteres alfanuméricos): ", "[a-zA-Z0-9 ]+");
            String coordenada = validador.obtenerStringValido("Ingrese la nueva coordenada (solo números): ", "\\d+");
            String descripcion = validador.obtenerStringValido("Ingrese la nueva descripcion (solo caracteres alfabéticos): ", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");

            direccionServicio.actualizarDireccion(id, municipioId, calle, carrera, coordenada, descripcion);

        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Asegúrese de ingresar un número correcto para los IDs.");
            scanner.next();
        }
    }

    private void eliminarDireccion() {
        try {
            int id = validador.obtenerIdValido("Ingrese el id de la direccion a eliminar (solo números enteros positivos): ");
            direccionServicio.eliminarDireccion(id);
            System.out.println("Dirección eliminada con éxito.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Asegúrese de ingresar un número entero para el ID.");
            scanner.next();
        }
    }   

    private void listarDirecciones() {
        direccionServicio.listarDirecciones();
    }

    private void guardarCSV() {
        guardarEnArchivo("CSV", direccionServicio::guardarEnArchivoCSV);
    }

    private void leerCSV() {
        direccionServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        guardarEnArchivo("BIN", direccionServicio::guardarEnArchivoBIN);
    }

    private void leerBIN() {
        direccionServicio.leerDesdeArchivoBIN();
    }

    private void guardarEnArchivo(String tipo, java.util.function.Consumer<Direccion> guardar) {
        try {
            int id = validador.obtenerIdValido("Ingrese el ID de la direccion: ");
            Direccion direccion = direccionServicio.consultarDireccion(id);
            if (direccion != null) {
                guardar.accept(direccion);
                System.out.println("Dirección guardada en archivo " + tipo + " con éxito.");
            } else {
                System.out.println("Dirección no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Asegúrese de ingresar un número entero para el ID.");
            scanner.next();
        }
    }

    private void obtenerDireccionCompleta() {
        int id = validador.obtenerIdValido("Ingrese el id de la dirección: ");
        String direccionCompleta = direccionServicio.consultaDireccionCompleta(id);
        System.out.println("Dirección completa: " + direccionCompleta);
    }
}
