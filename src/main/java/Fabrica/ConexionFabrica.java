/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;


import Interface.IGestorDatos;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author wrydmoon
 */
public class ConexionFabrica {
    
    private static final Map<Class<?>, IGestorDatos<?>> gestoresDatos = new HashMap<>();

    public static <T> void setGestorDatos(Class<T> modelo, IGestorDatos<T> gestorDatos) {
        gestoresDatos.put(modelo, gestorDatos);
    }

    @SuppressWarnings("unchecked")
    public static <T> IGestorDatos<T> getGestorDatos(Class<T> modelo) {
        if (!gestoresDatos.containsKey(modelo)) {
            throw new IllegalStateException("No se ha configurado un gestor de datos para " + modelo.getSimpleName());
        }
        return (IGestorDatos<T>) gestoresDatos.get(modelo);
    }
}
