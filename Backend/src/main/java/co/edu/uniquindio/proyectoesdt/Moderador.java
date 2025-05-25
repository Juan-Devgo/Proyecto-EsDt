package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.Patrones.*;

import java.util.LinkedList;

public class Moderador extends Usuario {
    private final GestorComandosModerador gestor = new GestorComandosModerador();

    public Moderador(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);
        this.activo = true;
    }

    @Override
    public Moderador clonar(String nickname) {
        Moderador moderador = new Moderador(nombre, nickname, contrasenia, carrera);
        moderador.setIntereses(intereses);
        moderador.setAmigos(amigos);
        moderador.setSeguidores(seguidores);
        moderador.setSeguidos(seguidos);
        moderador.setNumeroConexiones(numeroConexiones);
        moderador.setActivo(activo);

        return moderador;
    }

    public void ejecutarComando(ComandoModerador comando) {
        gestor.setComando(comando);
        gestor.ejecutarComando();
    }

    public void ejecutarEliminarPublicacion(Publicacion publicacion) {

        ejecutarComando(new ComandoEliminarPublicacion(publicacion, this));
    }

    public void ejecutarEliminarGrupo(GrupoEstudio grupo) {
        ejecutarComando(new ComandoEliminarGrupo(grupo, this));

    }

    public void ejecutarBanearEstudiante(Estudiante estudiante) {
        ejecutarComando(new ComandoBanearEstudiante(estudiante, this));
    }

    public void ejecutarDesbanearEstudiante(Estudiante estudiante) {
        ejecutarComando(new ComandoDesbanearEstudiante(estudiante, this));
    }

    public void eliminarEstudiante(Estudiante estudiante){
        ejecutarComando(new ComandoEliminarEstudiante(estudiante,this));
    }

    public LinkedList<Usuario> ejecutarObtenerUsuariosConexiones() {
        return Plataforma.getInstancia().obtenerUsuariosMasConexiones();
    }

    public LinkedList<Publicacion> obtenerPublicacionesValoradas() {
        return Plataforma.getInstancia().obtenerPublicacionesMasValoradas();
    }

    public String obtenerCaminoCorto(Usuario usuario1, Usuario usuario2){
        return Plataforma.getInstancia().obtenerCaminoMasCorto(usuario1,usuario2);
    }

    public LinkedList<GrupoEstudio> obtenerGruposMasParticipacion() {
        return Plataforma.getInstancia().obtenerGruposMasValorados();
    }

    public int[][] ejecutarObtenerGrafo() {
        return Plataforma.getInstancia().generarGrafo();
    }



}
