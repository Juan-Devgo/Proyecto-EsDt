package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Plataforma;

public class ComandoBanearEstudiante implements ComandoModerador {
    private final Estudiante estudiante;
    private final Moderador moderador;

    public ComandoBanearEstudiante(Moderador moderador, Estudiante estudiante) {
        this.moderador = moderador;
        this.estudiante = estudiante;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().
        estudiante.banear();
    }
}
