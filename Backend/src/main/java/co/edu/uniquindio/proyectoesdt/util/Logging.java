package co.edu.uniquindio.proyectoesdt.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static final Logger logger = Logger.getLogger("BrainLoop Logger");
    private static final FileHandler fHandler;

    static {
        try {
            Path path = Path.of(Archivos.obtenerRutaLogging());
            fHandler = new FileHandler(path.toString(), true);
            fHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fHandler);
        } catch (IOException e) {
            throw new RuntimeException("Error al configurar el Logging: " + e.getMessage(), e);
        }
    }

    private Logging() {}

    public static void log(String mensaje, Level nivel, Object clase) {
        logger.logp(nivel, clase.getClass().getSimpleName(), "", mensaje);
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