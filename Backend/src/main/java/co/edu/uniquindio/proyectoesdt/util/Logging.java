package co.edu.uniquindio.proyectoesdt.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static Logging instancia;
    private static final FileHandler fHandler;

    static {
        try {
            fHandler = new FileHandler("logging.log", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Logging() {
    }

    public static void log(String mensaje, Level nivel, Object clase) {
        if (instancia == null) {
            instancia = new Logging();
        }

        Logger logger = Logger.getLogger(clase.getClass().getName());
        fHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fHandler);

        logger.log(nivel, mensaje);
    }

    public static void logInfo(String mensaje, Object clase) {
        log(mensaje, Level.INFO, clase);
    }

    public static void logWarning(String mensaje, Object clase) {
        log(mensaje, Level.WARNING, clase);
    }

    public static void logSevere(String mensaje, Object clase) {
        log(mensaje, Level.SEVERE, clase);
    }
}
