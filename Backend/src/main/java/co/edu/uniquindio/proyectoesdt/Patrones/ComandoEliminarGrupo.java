package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.GrupoEstudio;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.util.Logging;

public class ComandoEliminarGrupo implements ComandoModerador {
    private final GrupoEstudio grupo;
    private final Moderador moderador;

    public ComandoEliminarGrupo(GrupoEstudio grupo, Moderador moderador) {
        this.grupo = grupo;
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        Plataforma.getInstancia().eliminarGrupoEstudio(grupo);
        Logging.logInfo("El moderador " + moderador.getNickname() + " ha eliminado el grupo de estudio "
                + grupo.getNombre() + ".", this);
    }
}

