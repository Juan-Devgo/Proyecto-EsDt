package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Publicacion;
import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoEliminarPublicacion implements ComandoModerador{

    private Moderador moderador;
    private Publicacion publicacion;

    public ComandoEliminarPublicacion(Moderador moderador, Publicacion publicacion) {
        this.moderador = moderador;
        this.publicacion = publicacion;
    }

    @Override
    public void ejecutar() {
        moderador.borrarPublicacion(publicacion);
        System.out.println("Publicación eliminada correctamente.");
    }
}
