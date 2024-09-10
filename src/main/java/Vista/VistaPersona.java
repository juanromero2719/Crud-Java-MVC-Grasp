package Vista;

import Servicio.PersonaServicio;
import Ubicacion.Direccion;
import Usuario.Persona;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * @author wrydmoon
 */
public class VistaPersona {

    private final PersonaServicio personaServicio;
    private final Scanner scanner;
    private final Validador validador;

    public VistaPersona() {
        this.personaServicio = new PersonaServicio();
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
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV");
                System.out.println("8. Guardar BIN");
                System.out.println("9. Leer BIN");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion >= 0 && opcion <= 9) {
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

    private void agregarPersona() {
        int id = validador.obtenerIdValido("Ingrese el ID de la persona (solo números enteros positivos): ");
        String nombres = validador.obtenerStringValido("Ingrese los nombres de la persona (solo caracteres alfabéticos y espacios): ", "[a-zA-Z0-9 ]+");
        String apellidos = validador.obtenerStringValido("Ingrese los apellidos de la persona (solo caracteres alfabéticos y espacios): ", "[a-zA-Z0-9 ]+");
        int direccionId = validador.obtenerIdValido("Ingrese el ID de la dirección (solo números enteros positivos): ");
        personaServicio.agregarPersona(id, nombres, apellidos, direccionId);
        System.out.println("Persona agregada con éxito.");
    }

    private void consultarPersona() {
        int id = validador.obtenerIdValido("Ingrese el ID de la persona (solo números): ");
        Persona persona = personaServicio.consultarPersona(id);
        System.out.println(persona != null ? persona.toString() : "Persona no encontrada.");
    }

    private void actualizarPersona() {
        
        int id = validador.obtenerIdValido("Ingrese el ID de la persona a actualizar (solo números): ");
        Persona personaExistente = personaServicio.consultarPersona(id);

        if (personaExistente == null) {
            System.out.println("Persona no encontrada.");
            return;
        }

        String nombres = validador.obtenerStringValido("Ingrese los nuevos nombres de la persona (solo caracteres alfabéticos y espacios): ", "[a-zA-Z0-9 ]+");
        String apellidos = validador.obtenerStringValido("Ingrese los nuevos apellidos de la persona (solo caracteres alfabéticos y espacios): ", "[a-zA-Z0-9 ]+");
        int direccionId = validador.obtenerIdValido("Ingrese el nuevo ID de la dirección (solo números): ");
        personaServicio.actualizarPersona(id, nombres, apellidos, direccionId);
        System.out.println("Persona actualizada con exito.");
        
    }

    private void eliminarPersona() {
        int id = validador.obtenerIdValido("Ingrese el ID de la persona a eliminar (solo números): ");
        Persona personaExistente = personaServicio.consultarPersona(id);

        if (personaExistente == null) {
            System.out.println("Persona no encontrada.");
        } else {
            personaServicio.eliminarPersona(id);
            System.out.println("Persona eliminada con éxito.");
        }
    }

    private void listarPersonas() {
        personaServicio.listarPersonas();
    }

    private void guardarCSV() {
        guardarArchivo("CSV", personaServicio::guardarEnArchivoCSV);
    }

    private void leerCSV() {
        personaServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        guardarArchivo("BIN", personaServicio::guardarEnArchivoBIN);
    }

    private void leerBIN() {
        personaServicio.leerDesdeArchivoBIN();
    }

    private void guardarArchivo(String tipoArchivo, java.util.function.Consumer<Persona> guardarFuncion) {
        int id = validador.obtenerIdValido("Ingrese el ID de la persona (solo números): ");
        Persona persona = personaServicio.consultarPersona(id);
        if (persona != null) {
            guardarFuncion.accept(persona);
            System.out.println("Persona guardada en archivo " + tipoArchivo + " con éxito.");
        } else {
            System.out.println("Persona no encontrada.");
        }
    }

}
