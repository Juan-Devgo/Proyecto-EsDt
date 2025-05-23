package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.MiColaPrioridad;
import co.edu.uniquindio.proyectoesdt.Publicacion;
import co.edu.uniquindio.proyectoesdt.Moderador;

public class ComandoObtenerPublicacionValoradas implements ComandoModerador {
    private Moderador moderador;

    public ComandoObtenerPublicacionValoradas(Moderador moderador) {
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        MiColaPrioridad<Publicacion> publicaciones = moderador.obtenerPublicacionesValoradas();
        System.out.println("Publicaciones mejor valoradas:");
        for (Publicacion p : publicaciones) {
            System.out.println(p.getTitulo() + " - " + p.getCantidadLikes() + " Likes");
        }
    }
}
