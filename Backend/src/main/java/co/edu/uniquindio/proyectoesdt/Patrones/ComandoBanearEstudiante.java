package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.util.Logging;

public class ComandoBanearEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoBanearEstudiante(Estudiante estudiante, Moderador moderador) {
        this.estudiante = estudiante;
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().banearEstudiante(estudiante);
        Logging.logInfo("El moderador " + moderador.getNickname() + " ha baneado al usuario " +
                estudiante.getNickname() + ".",this);
    }
}
