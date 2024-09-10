package Vista;

import Servicio.PaisServicio;
import Ubicacion.Pais;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaPais {

    private PaisServicio paisServicio;
    private Scanner scanner;
    private final Validador validador;
    
    public VistaPais() {
        this.paisServicio = new PaisServicio();
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
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        int opcion = -1;
        while (true) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de País:");
                System.out.println("1. Agregar País");
                System.out.println("2. Consultar País");
                System.out.println("3. Listar Países");
                System.out.println("4. Actualizar País");
                System.out.println("5. Eliminar País");
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV ");
                System.out.println("8. Guardar BIN");
                System.out.println("9. Leer BIN");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion < 0 || opcion > 9) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                } else {
                    return opcion;
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.next();
            }
        }
    }

    private void agregarPais() {
        int id = validador.obtenerIdValido("Ingrese el ID del país (solo números enteros positivos): ");
        String nombre = validador.obtenerStringValido("Ingrese el nombre del país (solo caracteres alfabéticos y al menos 3 caracteres): ", "[a-zA-Z0-9 ]+");
        
        paisServicio.agregarPais(id ,nombre);
    }

    private void consultarPais() {
        int id = validador.obtenerIdValido("Ingrese el ID del país (solo números): ");
        Pais pais = paisServicio.obtenerPais(id);
        if (pais != null) {
            System.out.println(pais.toString());
        } else {
            System.out.println("País no encontrado.");
        }
    }

    private void actualizarPais() {
        int id = validador.obtenerIdValido("Ingrese el ID del país a actualizar (solo números): ");
        String nombre = validador.obtenerStringValido("Ingrese el nuevo nombre del país (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        
        paisServicio.actualizarPais(id, nombre);
        System.out.println("País actualizado con éxito.");
    }

    private void eliminarPais() {
        int id = validador.obtenerIdValido("Ingrese el ID del país a eliminar (solo números): ");
        paisServicio.eliminarPais(id);
        System.out.println("País eliminado con éxito.");
    }

    private void listarPaises() {
        paisServicio.listarPaises();
    }

    private void guardarCSV() {
        guardarArchivo("CSV", paisServicio::guardarEnArchivoCSV);
    }

    private void leerCSV() {
        paisServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        guardarArchivo("BIN", paisServicio::guardarEnArchivoBIN);
    }

    private void leerBIN() {
        paisServicio.leerDesdeArchivoBIN();
    }

    private void guardarArchivo(String tipoArchivo, java.util.function.Consumer<Pais> guardarFunc) {
        int id = validador.obtenerIdValido("Ingrese el ID del país: ");
        Pais pais = paisServicio.obtenerPais(id);
        if (pais != null) {
            guardarFunc.accept(pais);
            System.out.println("País guardado en archivo " + tipoArchivo + " con éxito.");
        } else {
            System.out.println("País no encontrado.");
        }
    }

}
