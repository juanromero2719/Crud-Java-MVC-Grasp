package Vista;


import Servicio.EstudianteServicio;
import Usuario.Estudiante;
import Usuario.Persona;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaEstudiante {

    private final Scanner scanner;
    private final Validador validador;
    private final EstudianteServicio estudianteServicio;

    public VistaEstudiante() {
        
        this.estudianteServicio = new EstudianteServicio();
        this.scanner = new Scanner(System.in);
        this.validador = new Validador();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = obtenerOpcionMenu();
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private int obtenerOpcionMenu() {
        int opcion = -1;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("-------------------");
                System.out.println("Menu CRUD de estudiantes:");
                System.out.println("1. Agregar estudiante");
                System.out.println("2. Consultar estudiante");
                System.out.println("3. Listar estudiante");
                System.out.println("4. Actualizar estudiante");
                System.out.println("5. Eliminar estudiantes");
                System.out.println("6. Guardar CSV");
                System.out.println("7. Leer CSV");
                System.out.println("8. Guardar BIN");
                System.out.println("9. Leer BIN");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                inputValido = opcion >= 0 && opcion <= 9;
                if (!inputValido) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.next();
            }
        }
        return opcion;
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 ->
                agregarEstudiante();
            case 2 ->
                consultarEstudiante();
            case 3 ->
                listarEstudiantes();
            case 4 ->
                actualizarEstudiante();
            case 5 ->
                eliminarEstudiante();
            case 6 ->
                guardarCSV();
            case 7 ->
                leerCSV();
            case 8 ->
                guardarBIN();
            case 9 ->
                leerBIN();
            case 0 ->
                System.out.println("Saliendo...");
            default ->
                System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private void agregarEstudiante() {
        int estudianteId = validador.obtenerIdValido("Ingrese el ID del estudiante (solo números enteros positivos): ");
        String codigo = validador.obtenerStringValido("Ingrese el código del estudiante (solo números): ", "[a-zA-Z0-9 ]+");
        String programa = validador.obtenerStringValido("Ingrese el programa del estudiante (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
        double promedio = validador.obtenerDoubleValido("Ingrese el promedio del estudiante (número decimal): ");
        int personaId = validador.obtenerIdValido("Ingrese el ID de la persona (solo números enteros positivos): ");

        estudianteServicio.agregarEstudiante(estudianteId, codigo, programa, promedio, personaId);
       
    }

    private void consultarEstudiante() {
        int id = validador.obtenerIdValido("Ingrese el ID del estudiante (solo números): ");
        Estudiante estudiante = estudianteServicio.consultarEstudiante(id);
        System.out.println(estudiante != null ? estudiante.toString() : "Estudiante no encontrado.");
    }

    private void actualizarEstudiante() {
        int id = validador.obtenerIdValido("Ingrese el ID del estudiante (solo números): ");
        Estudiante estudiante = estudianteServicio.consultarEstudiante(id);

        if (estudiante != null) {
            
            String codigo = validador.obtenerStringValido("Ingrese el nuevo código del estudiante (solo números): ", "[a-zA-Z0-9 ]+");
            String programa = validador.obtenerStringValido("Ingrese el nuevo programa del estudiante (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
            double promedio = validador.obtenerDoubleValido("Ingrese el nuevo promedio del estudiante (número decimal): ");
            int personaId = validador.obtenerIdValido("Ingrese el nuevo ID de la persona (solo números): ");
   
            estudianteServicio.actualizarEstudiante(id, codigo, programa, promedio, personaId);
           
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private void eliminarEstudiante() {
        int id = validador.obtenerIdValido("Ingrese el ID del estudiante (solo números enteros positivos): ");
        estudianteServicio.eliminarEstudiante(id);
    }

    private void listarEstudiantes() {
        estudianteServicio.listarEstudiantes();
    }

    private void guardarCSV() {
        int id = validador.obtenerIdValido("Ingrese el ID del Estudiante: ");
        Estudiante estudiante = estudianteServicio.consultarEstudiante(id);
        if (estudiante != null) {
            estudianteServicio.guardarEnArchivoCSV(id);
            System.out.println("Estudiante guardado en archivo CSV con éxito.");
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private void leerCSV() {
        estudianteServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        int id = validador.obtenerIdValido("Ingrese el ID del estudiante: ");
        Estudiante estudiante = estudianteServicio.consultarEstudiante(id);
        if (estudiante != null) {
            estudianteServicio.guardarEnArchivoBIN(id);
            System.out.println("Estudiante guardado en archivo BIN con éxito.");
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private void leerBIN() {
        estudianteServicio.leerDesdeArchivoBIN();
    }
}
