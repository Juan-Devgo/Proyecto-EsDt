package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Publicacion;

public class ComandoBorrarPublicacion implements ComandoModerador {
    private final Publicacion publicacion;
    private final Moderador moderador;

    public ComandoBorrarPublicacion(Moderador moderador, Publicacion publicacion) {
        this.moderador = moderador;
        this.publicacion = publicacion;
    }

    @Override
    public void ejecutar() {
        moderador.borrarPublicacion(publicacion);
    }
}
