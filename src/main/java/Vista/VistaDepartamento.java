package Vista;


import Controlador.PaisController;
import Servicio.DepartamentoServicio;
import Ubicacion.Departamento;
import Ubicacion.Pais;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaDepartamento {

    private final DepartamentoServicio departamentoServicio;
    private final PaisController paisController;
    private final Scanner scanner;
    private final Validador validador;

    public VistaDepartamento() {
        this.departamentoServicio = new DepartamentoServicio();
        this.paisController = new PaisController();
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
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV ");
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

    private void agregarDepartamento() {
        int id = validador.obtenerIdValido("Ingrese el ID del Departamento: ");
        String nombre = validador.obtenerStringValido("Ingrese el nombre del departamento (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        int paisId = validador.obtenerIdValido("Ingrese el ID del país (solo números enteros positivos): ");

        try {
            departamentoServicio.agregarDepartamento(id, nombre, paisId);
            System.out.println("Departamento agregado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void consultarDepartamento() {
        int id = validador.obtenerIdValido("Ingrese el ID del departamento (solo números enteros positivos): ");
        Departamento departamento = departamentoServicio.consultarDepartamento(id);
        if (departamento != null) {
            System.out.println("Departamento: " + departamento.getNombre());
        } else {
            System.out.println("Departamento no encontrado.");
        }
    }

    private void actualizarDepartamento() {
        int id = validador.obtenerIdValido("Ingrese el ID del departamento a actualizar (solo números enteros positivos): ");
        String nombre = validador.obtenerStringValido("Ingrese el nuevo nombre del departamento (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        int paisId = validador.obtenerIdValido("Ingrese el nuevo ID del país (solo números enteros positivos): ");

        try {
            departamentoServicio.actualizarDepartamento(id, nombre, paisId);
            System.out.println("Departamento actualizado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarDepartamento() {
        int id = validador.obtenerIdValido("Ingrese el ID del departamento a eliminar (solo números enteros positivos): ");
        departamentoServicio.eliminarDepartamento(id);
    }

    private void listarDepartamentos() {
        departamentoServicio.listarDepartamentos();
    }

    private void guardarCSV() {
        int id = validador.obtenerIdValido("Ingrese el ID del departamento: ");
        Departamento departamento = departamentoServicio.consultarDepartamento(id);
        if (departamento != null) {
            departamentoServicio.guardarEnArchivoCSV(departamento);
            System.out.println("Departamento guardado en archivo CSV con éxito.");
        } else {
            System.out.println("Departamento no encontrado.");
        }
    }

    private void leerCSV() {
        departamentoServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        int id = validador.obtenerIdValido("Ingrese el ID del departamento: ");
        Departamento departamento = departamentoServicio.consultarDepartamento(id);
        if (departamento != null) {
            departamentoServicio.guardarEnArchivoBIN(departamento);
            System.out.println("Departamento guardado en archivo BIN con éxito.");
        } else {
            System.out.println("Departamento no encontrado.");
        }
    }

    private void leerBIN() {
        departamentoServicio.leerDesdeArchivoBIN();
    }

}
