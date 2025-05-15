package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoObtenerGrafo implements ComandoModerador{
    private Moderador moderador;

    public ComandoObtenerGrafo(Moderador moderador) {
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        moderador.obtenerGrafo(); // puedes devolver el resultado si lo necesitas
    }
}
