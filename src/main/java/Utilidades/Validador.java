package Utilidades;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validador {
    private final Scanner scanner;

    public Validador() {
        this.scanner = new Scanner(System.in);
    }

    public int obtenerIdValido(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int id = scanner.nextInt();
                scanner.nextLine();
                if (id >= 0) {
                    return id;
                } else {
                    System.out.println("Entrada inválida. El ID debe ser un número entero positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. El ID debe ser un número entero positivo.");
                scanner.next(); 
            }
        }
    }

    public String obtenerStringValido(String mensaje, String patron) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine();
            if (input.matches(patron)) {
                return input;
            } else {
                System.out.println("Entrada no válida. Intente nuevamente.");
            }
        }
    }
    
    public double obtenerDoubleValido(String mensaje) {
        double valor = 0;
        boolean valorValido = false;
        while (!valorValido) {
            System.out.print(mensaje);
            String valorStr = scanner.nextLine();
            try {
                valor = Double.parseDouble(valorStr);
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. El valor debe ser un número.");
            }
        }
        return valor;
    }
}
