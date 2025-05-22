package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.Patrones.*;

public class Moderador extends Usuario {

    private final GestorComandosModerador gestor = new GestorComandosModerador();

    public Moderador(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);
        this.activo = true;

    }

    public void ejecutarComando(ComandoModerador comando) {
        gestor.setComando(comando);
        gestor.ejecutarComando();
    }

    public void ejecutarObtenerGrafo() {
        ejecutarComando(new ComandoObtenerGrafo(this));
    }

    public void ejecutarBorrarPublicacion(Publicacion publicacion) {
        ejecutarComando(new ComandoEliminarPublicacion(this, publicacion));
    }



    public void ejecutarEliminarGrupo(GrupoEstudio grupo) {
        ejecutarComando(new ComandoEliminarGrupo(this, grupo));
    }

    public void ejecutarBanearEstudiante(Estudiante estudiante) {
        ejecutarComando(new ComandoBanearEstudiante(this, estudiante));
    }

    public void ejecutarDesbanearEstudiante(Estudiante estudiante) {
        ejecutarComando(new ComandoDesbanearEstudiante(this, estudiante));
    }

    public void ejecutarObtenerUsuariosConexiones() {
        ejecutarComando(new ComandoObtenerUsuarioConexiones(this));
    }

    public void obtenerPublicacionesValoradas() {
        GestorComandosModerador gestor = new GestorComandosModerador();
        gestor.setComando(new ComandoObtenerPublicacionValoradas(this));
        gestor.ejecutarComando();
    }

    public void obtenerGruposMasParticipacion() {
        GestorComandosModerador gestor = new GestorComandosModerador();
        gestor.setComando(new ComandoObtenerGruposParticipacion(this));
        gestor.ejecutarComando();

    }

    public void ejecutarEliminarPublicacion(Publicacion publicacion) {
        GestorComandosModerador gestor = new GestorComandosModerador();
        gestor.setComando(new ComandoEliminarPublicacion(this, publicacion));
        gestor.ejecutarComando();
    }



}