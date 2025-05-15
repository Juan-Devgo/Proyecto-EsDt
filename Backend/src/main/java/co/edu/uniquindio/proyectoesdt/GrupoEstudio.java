package co.edu.uniquindio.proyectoesdt;

import java.util.ArrayList;
import java.util.HashSet;

public class GrupoEstudio implements InsertableBD, Comparable<GrupoEstudio> {
    private String nombre;
    private final ArrayList<String> temas;
    private final HashSet<Estudiante> solicitudes;
    private final HashSet<Estudiante> integrantes;
    private int numeroParticipantes = 0;

    public GrupoEstudio(String nombre) {
        if(nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del grupo de estudio es inválido");
        }
        this.numeroParticipantes = numeroParticipantes;
        this.temas = new ArrayList<>();
        this.solicitudes = new HashSet<>();
        this.integrantes = new HashSet<>();
    }

    public void recibirSolicitud(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al recibir solicitud.");
        }

        solicitudes.add(est);
    }

    public void rechazarSolicitud(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al rechazar solicitud.");
        }

        solicitudes.remove(est);
    }

    public void aceptarSolicitud(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al aceptar solicitud.");
        }

        solicitudes.remove(est);
        integrantes.add(est);
        numeroParticipantes++;
    }

    public void eliminarIntegrante(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al eliminar integrante.");
        }

        integrantes.remove(est);
        numeroParticipantes --;
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

    public HashSet<Estudiante> getSolicitudes() {
        return solicitudes;
    }

    public HashSet<Estudiante> getIntegrantes() {
        return integrantes;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    @Override
    public int compareTo(GrupoEstudio otroGrupo) {
        return Integer.compare(otroGrupo.getNumeroParticipantes(), this.getNumeroParticipantes());
    }
}
