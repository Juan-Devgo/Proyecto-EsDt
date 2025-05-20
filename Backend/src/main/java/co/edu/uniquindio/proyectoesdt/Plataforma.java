package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;

public class Plataforma {
    private static Plataforma instancia;
    public static final String nombre = "BrainLoop";
    private final GrafoNoDirigido<Usuario> usuarios;
    private final MiLinkedList<GrupoEstudio> gruposEstudio;
    private final ArbolBinarioABB<Publicacion> publicaciones;
    private final MiColaPrioridad<SolicitudAyuda> solicitudesAyuda;

    private Plataforma() {
        this.usuarios = new GrafoNoDirigido<>();
        this.gruposEstudio = new MiLinkedList<>();
        this.publicaciones = new ArbolBinarioABB<>();
        this.solicitudesAyuda = new MiColaPrioridad<>();
    }

    public static Plataforma getInstancia() {
        if (instancia == null) {
            instancia = new Plataforma();
        }
        return instancia;
    }

    // Metodo para generar grafo no dirigido: COMPLETAR
    public GrafoNoDirigido<Usuario> generarGrafo() {
        GrafoNoDirigido<Usuario> grafo = new GrafoNoDirigido<>();
        return grafo;
    }

    // Metodo para generar Grupos de Estudio: COMPLETAR
    public void generarGruposEstudio() {
    }


    public GrafoNoDirigido<Usuario> getUsuarios() {
        return usuarios;
    }

    public MiLinkedList<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public ArbolBinarioABB<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public MiColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }
}