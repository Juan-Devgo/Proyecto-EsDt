package co.edu.uniquindio.proyectoesdt.util;

import java.io.File;

public class Archivos {

    public static String limpiarNombreArchivo(String nombre) {
        return nombre.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static void asegurarExisteDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        if (!directorio.exists()) {
            boolean creado = directorio.mkdirs();
            if (!creado) {
                throw new RuntimeException("No se pudo crear el directorio: " + rutaDirectorio);
            }
        }
    }

    public static String obtenerRutaFiles() {
        String rutaFiles = "C:\\Users\\juand\\OneDrive\\Escritorio\\Uniquindío\\Semestre 5\\Estructura de Datos\\Proyecto-EsDt" +
                "\\Backend\\src\\main\\resources\\files";
        asegurarExisteDirectorio(rutaFiles);
        return rutaFiles;
    }

    public static String obtenerRutaLogging() {
        String rutaLogging = "C:\\Users\\juand\\OneDrive\\Escritorio\\Uniquindío\\Semestre 5\\Estructura de Datos\\Proyecto-EsDt" +
                "\\Backend\\src\\main\\resources\\logging.log";
        asegurarExisteDirectorio(rutaLogging);
        return rutaLogging;
    }
}
