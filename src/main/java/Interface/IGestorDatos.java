/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;

/**
 *
 * @author wrydmoon
 */
public interface IGestorDatos<T> {
    
    void crear(T t);
    T leer(int id);
    void listar();
    void actualizar(T t);
    void eliminar(int id);
    void guardarEnArchivoCSV(T t);
    void leerDesdeArchivoCSV();
    void guardarEnArchivoBIN(T t);
    void leerDesdeArchivoBIN();
}
