package co.edu.uniquindio.proyectoesdt;

import java.util.ArrayList;
import java.util.HashSet;

public class GrupoEstudio implements InsertableBD, Comparable<GrupoEstudio> {
    private final String nombre;
    private ArrayList<String> temas;
    private HashSet<Estudiante> solicitudes;
    private HashSet<Estudiante> integrantes;
    private int numeroParticipantes;

    public GrupoEstudio(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("No se puede crear un grupo de estudio sin nombre.");
        }

        this.nombre = nombre;
        this.temas = new ArrayList<>();
        this.solicitudes = new HashSet<>();
        this.integrantes = new HashSet<>();
        this.numeroParticipantes = 0;
    }

    @Override
    public int compareTo(GrupoEstudio otroGrupo) {
        return Integer.compare(otroGrupo.getNumeroParticipantes(), this.getNumeroParticipantes());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof GrupoEstudio g) {
            equal = nombre.equals(g.nombre);
        }

        return equal;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public GrupoEstudio clonar(String nombre) {
        GrupoEstudio grupo = new GrupoEstudio(nombre);
        grupo.setTemas(temas);
        grupo.setIntegrantes(integrantes);
        grupo.setSolicitudes(solicitudes);
        grupo.setNumeroParticipantes(numeroParticipantes);

        return grupo;
    }

    public void agregarTema(String tema) {
        if(tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("No se puede agregar un tema en blanco.");
        }

        temas.add(tema);
    }

    public void eliminarTema(String tema) {
        if(tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("No se puede eliminar un tema en blanco.");
        }

        temas.remove(tema);
    }

    public void recibirSolicitud(Estudiante est) {
        if (est == null) {
            throw new IllegalArgumentException("Valor nulo al recibir solicitud.");
        }

        solicitudes.add(est);
    }

    public void rechazarSolicitud(Estudiante est) {
        if (est == null) {
            throw new IllegalArgumentException("Valor nulo al rechazar solicitud.");
        }

        solicitudes.remove(est);
    }

    public void aceptarSolicitud(Estudiante est) {
        if (est == null) {
            throw new IllegalArgumentException("Valor nulo al aceptar solicitud.");
        }

        solicitudes.remove(est);
        integrantes.add(est);
        est.agregarGrupoEstudio(this);
        numeroParticipantes++;
    }

    public void eliminarIntegrante(Estudiante est) {
        if (est == null) {
            throw new IllegalArgumentException("Valor nulo al eliminar integrante.");
        }

        integrantes.remove(est);
        numeroParticipantes--;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<String> temas) {
        if (temas == null) {
            throw new IllegalArgumentException("Valor nulo al cambiar los temas.");
        }

        this.temas = temas;
    }

    public HashSet<Estudiante> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(HashSet<Estudiante> solicitudes) {
        if (solicitudes == null) {
            throw new IllegalArgumentException("Valor nulo al cambiar las solicitudes.");
        }

        this.solicitudes = solicitudes;
    }

    public HashSet<Estudiante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(HashSet<Estudiante> integrantes) {
        if (solicitudes == null) {
            throw new IllegalArgumentException("Valor nulo al cambiar los integrantes.");
        }

        this.integrantes = integrantes;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }
}