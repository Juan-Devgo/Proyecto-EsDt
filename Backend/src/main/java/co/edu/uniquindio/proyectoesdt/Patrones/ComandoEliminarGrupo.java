package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.GrupoEstudio;
import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoEliminarGrupo implements ComandoModerador{
    private final GrupoEstudio grupoEstudio;
    private final Moderador moderador;

    public ComandoEliminarGrupo(Moderador moderador, GrupoEstudio grupoEstudio) {
        this.moderador = moderador;
        this.grupoEstudio = grupoEstudio;
    }

    @Override
    public void ejecutar(){

    }
}
