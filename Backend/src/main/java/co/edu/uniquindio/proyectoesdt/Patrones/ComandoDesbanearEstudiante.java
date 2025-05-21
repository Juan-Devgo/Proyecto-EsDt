package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoDesbanearEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoDesbanearEstudiante(Moderador moderador, Estudiante estudiante) {
        this.moderador = moderador;
        this.estudiante = estudiante;
    }

    @Override
    public void ejecutar() {
        estudiante.desbanear();
    }
}
