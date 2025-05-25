package co.edu.uniquindio.proyectoesdt;

import java.util.HashSet;

public class Estudiante extends Usuario {
    private HashSet<GrupoEstudio> gruposEstudio;

    public Estudiante(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);

        this.gruposEstudio = new HashSet<>();
        this.activo = true;
    }

    @Override
    public Estudiante clonar(String nickname) {
        Estudiante estudiante = new Estudiante(nombre, nickname, contrasenia, carrera);
        estudiante.setIntereses(intereses);
        estudiante.setAmigos(amigos);
        estudiante.setSeguidores(seguidores);
        estudiante.setSeguidos(seguidos);
        estudiante.setNumeroConexiones(numeroConexiones);
        estudiante.setActivo(activo);
        estudiante.setGruposEstudio(gruposEstudio);

        return estudiante;
    }

    //Metodo para banear al estudiante, Activo = False
    public void banear() {
        this.activo = false;
    }

    //Metodo para desbanear al estudiante, Activo = True
    public void desbanear() {
        this.activo = true;
    }

    public void solicitarCupoGrupo(GrupoEstudio gru) {
        if (gru == null) {
            throw new IllegalArgumentException("Valor nulo al solicitar cupo grupo");
        }

        gru.recibirSolicitud(this);
    }

    public void dejarDeSolicitarCupoGrupo(GrupoEstudio gru) {
        if (gru == null) {
            throw new IllegalArgumentException("Valor nulo al dejar de solicitar cupo grupo");
        }

        gru.rechazarSolicitud(this);
    }

    public void agregarGrupoEstudio(GrupoEstudio gru) {
        if (gru == null) {
            throw new IllegalArgumentException("Valor nulo al agregar un grupo");
        }

        gruposEstudio.add(gru);
    }

    public HashSet<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public void setGruposEstudio(HashSet<GrupoEstudio> gruposEstudio) {
        this.gruposEstudio = gruposEstudio;
    }
}