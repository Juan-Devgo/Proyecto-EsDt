package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.GrupoEstudio;

public class ComandoObtenerGruposParticipacion implements ComandoModerador{
    private Moderador moderador;

    public ComandoObtenerGruposParticipacion(Moderador moderador) {
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        ListaPrioridad<GrupoEstudio> grupos = moderador.obtenerGruposMasParticipacion();
        System.out.println("Grupos con mayor participación:");
        for (GrupoEstudio grupo : grupos) {
            System.out.println(grupo.getNombre() + " - " + grupo.getNumeroParticipantes() + " estudiantes");
        }
    }
}
