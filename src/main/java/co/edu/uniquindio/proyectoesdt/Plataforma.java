package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;

public class Plataforma {
    private static Plataforma instancia;
    public static final String nombre = "BrainLoop";
    private final GrafoNoDirigido<Usuario> usuarios;
    private final MiLinkedList<GrupoEstudio> gruposEstudio;
    private final ArbolBiBusqueda<Publicacion> publicaciones;
    private final MiColaPrioridad<SolicitudAyuda> solicitudesAyuda;

    private Plataforma() {
        this.usuarios = new GrafoNoDirigido<>();
        this.gruposEstudio = new MiLinkedList<>();
        this.publicaciones = new ArbolBiBusqueda<>();
        this.solicitudesAyuda = new MiColaPrioridad<>();
    }

    public static Plataforma getInstancia() {
        if(instancia == null) {
            instancia = new Plataforma();
        }
        return instancia;
    }

    public GrafoNoDirigido<Usuario> getUsuarios() {
        return usuarios;
    }

    public MiLinkedList<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public ArbolBiBusqueda<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public MiColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }
}