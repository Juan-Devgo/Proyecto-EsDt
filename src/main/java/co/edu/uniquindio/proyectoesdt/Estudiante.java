package co.edu.uniquindio.proyectoesdt;

import java.util.HashSet;

public class Estudiante extends Usuario{
    private final HashSet<Estudiante> conexiones;
    private final HashSet<GrupoEstudio> gruposEstudio;
    private int numeroConexiones = 0;
    protected boolean activo;
    // atributo Intereses

    public Estudiante(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);

        this.numeroConexiones = numeroConexiones;
        this.conexiones = new HashSet<>();
        this.gruposEstudio = new HashSet<>();
    }

    //Metodo para banear al estudiante, Activo = false
    public void banear() {
        this.activo = false;
    }

    //Metodo para desbanear al estudiante, Activo = True
    public void desbanear() {
        this.activo = true;
    }

    public void agregarConexion(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al agregar conexión");
        }

        conexiones.add(est);
        numeroConexiones++;
    }

    public void eliminarConexion(Estudiante est) {
        if(est == null) {
            throw new IllegalArgumentException("Valor nulo al eliminar conexión");
        }

        conexiones.remove(est);
        numeroConexiones--;
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
    // Metodo para obtener la lista de grupos sugeridos.


    public int getNumeroConexiones() {
        return numeroConexiones;
    }

    public HashSet<Estudiante> getConexiones() {
        return conexiones;
    }

    public HashSet<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }
}