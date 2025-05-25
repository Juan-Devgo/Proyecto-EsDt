package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Publicacion;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.util.Logging;

public class ComandoEliminarPublicacion implements ComandoModerador {
    private final Publicacion publicacion;
    private final Moderador moderador;

    public ComandoEliminarPublicacion(Publicacion publicacion, Moderador moderador) {
        this.publicacion = publicacion;
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().eliminarPublicacion(publicacion);
        Logging.logInfo("El moderador " + moderador.getNickname() + " ha eliminado la publicación "
                + publicacion.getTitulo() + ".", this);
    }
}
