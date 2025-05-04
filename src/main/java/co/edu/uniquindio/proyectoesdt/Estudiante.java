package co.edu.uniquindio.proyectoesdt;

import java.util.HashSet;

public class Estudiante extends Usuario{
    private final HashSet<Estudiante> conexiones;
    private final HashSet<GrupoEstudio> gruposEstudio;

    public Estudiante(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);

        this.conexiones = new HashSet<>();
        this.gruposEstudio = new HashSet<>();
    }


    public void agregarConexion(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al agregar conexión");
        }

        conexiones.add(est);
    }

    public void eliminarConexion(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al eliminar conexión");
        }

        conexiones.remove(est);
    }

    public void solicitarCupoGrupo(GrupoEstudio gru) {
        if(gru == null) {
            throw new IllegalArgumentException("Valor nulo al solicitar cupo grupo");
        }

        gru.recibirSolicitud(this);
    }

    public void dejarDeSolicitarCupoGrupo(GrupoEstudio gru) {
        if(gru == null) {
            throw new IllegalArgumentException("Valor nulo al dejar de solicitar cupo grupo");
        }

        gru.rechazarSolicitud(this);
    }

    public HashSet<Estudiante> getConexiones() {
        return conexiones;
    }

    public HashSet<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }
}