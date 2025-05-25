package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.util.Logging;

public class ComandoDesbanearEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoDesbanearEstudiante(Estudiante estudiante, Moderador moderador) {
        this.moderador = moderador;
        this.estudiante = estudiante;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().desbanearEstudiante(estudiante);
        Logging.logInfo("El moderador " + moderador.getNickname() + " ha desbaneado al usuario " +
                estudiante.getNickname() + ".",this);
    }
}