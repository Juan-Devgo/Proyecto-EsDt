package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoEliminarEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoEliminarEstudiante(Estudiante estudiante, Moderador moderador) {
        this.estudiante = estudiante;
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {

    }


}
