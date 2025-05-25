package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.util.Logging;

public class ComandoEliminarEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoEliminarEstudiante(Estudiante estudiante, Moderador moderador) {
        this.estudiante = estudiante;
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().eliminarEstudiante(estudiante);
        Logging.logInfo("El moderador " + moderador.getNickname() + " ha eliminado al estudiante "
                + estudiante.getNickname() + ".", this);
    }
}