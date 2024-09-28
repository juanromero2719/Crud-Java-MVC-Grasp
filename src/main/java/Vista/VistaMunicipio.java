package Vista;

import Controlador.MunicipioController;
import Utilidades.Validador;
import java.util.Scanner;

public class VistaMunicipio {

    private final MunicipioController municipioController;    
    private final Scanner scanner;
    private final Validador validador;

    public VistaMunicipio() {
        this.municipioController = new MunicipioController();
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
                case 0 ->
                    System.out.println("Saliendo...");
                default ->
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void agregarMunicipio() {
                  
        int idMunicipio = validador.obtenerIdValido("Ingrese el ID del municipio (solo numeros enteros positivos): ");
        String nombreMunicipio = validador.obtenerStringValido("Ingrese el nombre del municipio (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        int idDepartamento = validador.obtenerIdValido("Ingrese el ID del departamento (solo numeros enteros positivos): ");            
        municipioController.agregarMunicipio(idMunicipio, nombreMunicipio, idDepartamento);
     
    }

    private void consultarMunicipio() {
        int idMunicipio = validador.obtenerIdValido("Ingrese el ID del municipio (solo numeros): ");       
        municipioController.consultarMunicipio(idMunicipio);

    }

    private void actualizarMunicipio() {
           
        int idMunicipio = validador.obtenerIdValido("Ingrese el ID del municipio a actualizar (solo numeros): ");
        String nombreMunicipio = validador.obtenerStringValido("Ingrese el nuevo nombre del municipio (solo caracteres alfabeticos): ", "[a-zA-Z0-9 ]+");
        int idDepartamento = validador.obtenerIdValido("Ingrese el nuevo ID del departamento (solo numeros enteros positivos): ");            
        municipioController.actualizarMunicipio(idMunicipio, nombreMunicipio, idDepartamento);
                   
    }

    private void eliminarMunicipio() {
        int idMunicipio = validador.obtenerIdValido("Ingrese el ID del municipio a eliminar (solo numeros): ");      
        municipioController.eliminarMunicipio(idMunicipio);
    }

    private void listarMunicipios() {
        municipioController.listarMunicipios();
    }
  
}
