package co.edu.uniquindio.proyectoesdt;

import java.util.ArrayList;
import java.util.HashSet;

public class GrupoEstudio implements InsertableBD, Comparable<GrupoEstudio> {
    private String nombre;
    private ArrayList<String> temas;
    private HashSet<Estudiante> solicitudes;
    private HashSet<Estudiante> integrantes;
    private int numeroParticipantes;

    public GrupoEstudio(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del grupo de estudio es inválido");
        }
        this.temas = new ArrayList<>();
        this.solicitudes = new HashSet<>();
        this.integrantes = new HashSet<>();
        this.numeroParticipantes = 0;
    }

    @Override
    public int compareTo(GrupoEstudio otroGrupo) {
        return Integer.compare(otroGrupo.getNumeroParticipantes(), this.getNumeroParticipantes());
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

}