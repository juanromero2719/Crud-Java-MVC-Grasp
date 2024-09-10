package Vista;

import Servicio.MunicipioServicio;
import Ubicacion.Departamento;
import Ubicacion.Municipio;
import Utilidades.Validador;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VistaMunicipio {

    private final MunicipioServicio municipioServicio;
    private final Scanner scanner;
    private final Validador validador;

    public VistaMunicipio() {
        this.municipioServicio = new MunicipioServicio();
        this.scanner = new Scanner(System.in);
        this.validador = new Validador();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            opcion = validador.obtenerIdValido(
                    "Menu CRUD de municipios:\n"
                    + "1. Agregar municipio\n"
                    + "2. Consultar municipio\n"
                    + "3. Listar municipio\n"
                    + "4. Actualizar municipio\n"
                    + "5. Eliminar municipios\n"
                    + "6. Guardar CSV\n"
                    + "7. Leer CSV\n"
                    + "8. Guardar BIN\n"
                    + "9. Leer BIN\n"
                    + "0. Salir\n"
                    + "Seleccione una opcion: ");

            switch (opcion) {
                case 1 ->
                    agregarMunicipio();
                case 2 ->
                    consultarMunicipio();
                case 3 ->
                    listarMunicipios();
                case 4 ->
                    actualizarMunicipio();
                case 5 ->
                    eliminarMunicipio();
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
        } while (opcion != 0);
    }

    private void agregarMunicipio() {
        
        try {
            
            int id = validador.obtenerIdValido("Ingrese el ID del municipio (solo números enteros positivos): ");
            String nombre = validador.obtenerStringValido("Ingrese el nombre del municipio (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
            int departamentoId = validador.obtenerIdValido("Ingrese el ID del departamento (solo números enteros positivos): ");
            municipioServicio.agregarMunicipio(id, nombre, departamentoId);
            
        }catch(InputMismatchException e) {
            
            System.out.println("Entrada no válida. Asegúrese de ingresar un número correcto para los IDs.");
            scanner.next();
        }
       
    }

    private void consultarMunicipio() {
        int id = validador.obtenerIdValido("Ingrese el ID del municipio (solo números): ");
        Municipio municipio = municipioServicio.consultarMunicipio(id);
        System.out.println(municipio != null ? municipio.toString() : "Municipio no encontrado.");
    }

    private void actualizarMunicipio() {
        try{
            
            int id = validador.obtenerIdValido("Ingrese el ID del municipio a actualizar (solo números): ");
            String nombre = validador.obtenerStringValido("Ingrese el nuevo nombre del municipio (solo caracteres alfabéticos): ", "[a-zA-Z0-9 ]+");
            int departamentoId = validador.obtenerIdValido("Ingrese el nuevo ID del departamento (solo números enteros positivos): ");
            municipioServicio.actualizarMunicipio(id, nombre, departamentoId);
            
        } catch (InputMismatchException e) {
            
            System.out.println("Entrada no válida. Asegúrese de ingresar un número correcto para los IDs.");
            scanner.next();
        }
    }

    private void eliminarMunicipio() {
        int id = validador.obtenerIdValido("Ingrese el ID del municipio a eliminar (solo números): ");
        municipioServicio.eliminarMunicipio(id);
        System.out.println("Municipio eliminado con éxito.");
    }

    private void listarMunicipios() {
        municipioServicio.listarMunicipios();
    }

    private void guardarCSV() {
        
        int id = validador.obtenerIdValido("Ingrese el ID del municipio: ");    
        municipioServicio.guardarEnArchivoCSV(id);
        
        
    }

    private void leerCSV() {
        municipioServicio.leerDesdeArchivoCSV();
    }

    private void guardarBIN() {
        int id = validador.obtenerIdValido("Ingrese el ID del municipio: ");      
        municipioServicio.guardarEnArchivoBIN(id);        
    }

    private void leerBIN() {
        municipioServicio.leerDesdeArchivoBIN();
    }
}
