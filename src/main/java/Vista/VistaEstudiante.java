package Vista;


import Controlador.EstudianteController;
import Utilidades.Validador;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaEstudiante {

    private final Scanner scanner;
    private final Validador validador;
    private final EstudianteController estudianteController;

    public VistaEstudiante() {
        
        this.estudianteController = new EstudianteController();
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
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                inputValido = opcion >= 0 && opcion <= 9;
                if (!inputValido) {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero.");
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
            case 0 ->
                System.out.println("Saliendo...");
            default ->
                System.out.println("Opción invalida. Intente nuevamente.");
        }
    }

    private void agregarEstudiante() {
        int idEstudiante = validador.obtenerIdValido("Ingrese el ID del estudiante (solo numeros enteros positivos): ");
        String codigo = validador.obtenerStringValido("Ingrese el codigo del estudiante (solo numeros): ", "[a-zA-Z0-9 ]+");
        String programa = validador.obtenerStringValido("Ingrese el programa del estudiante (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        double promedio = validador.obtenerDoubleValido("Ingrese el promedio del estudiante (numero decimal): ");
        int idPersona = validador.obtenerIdValido("Ingrese el ID de la persona (solo numeros enteros positivos): ");
        estudianteController.agregarEstudiante(idEstudiante, codigo, programa, promedio, idPersona);
       
    }

    private void consultarEstudiante() {
        int idEstudiante = validador.obtenerIdValido("Ingrese el ID del estudiante (solo numeros): ");
        estudianteController.consultarEstudiante(idEstudiante);
    }

    private void actualizarEstudiante() {
        
        int idEstudiante = validador.obtenerIdValido("Ingrese el ID del estudiante (solo numeros): ");           
        String codigo = validador.obtenerStringValido("Ingrese el nuevo codigo del estudiante (solo numeros): ", "[a-zA-Z0-9 ]+");
        String programa = validador.obtenerStringValido("Ingrese el nuevo programa del estudiante (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        double promedio = validador.obtenerDoubleValido("Ingrese el nuevo promedio del estudiante (numero decimal): ");
        int idPersona = validador.obtenerIdValido("Ingrese el nuevo ID de la persona (solo numeros): ");  
        estudianteController.actualizarEstudiante(idEstudiante, codigo, programa, promedio, idPersona);
           
        
    }

    private void eliminarEstudiante() {
        int idEstudiante = validador.obtenerIdValido("Ingrese el ID del estudiante (solo numeros enteros positivos): ");
        estudianteController.eliminarEstudiante(idEstudiante);
    }

    private void listarEstudiantes() {
        estudianteController.listarEstudiantes();
    }
    
}
