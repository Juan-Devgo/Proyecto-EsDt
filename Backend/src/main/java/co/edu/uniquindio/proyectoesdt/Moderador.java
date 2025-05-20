package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.GrafoNoDirigido;
import co.edu.uniquindio.proyectoesdt.EstructurasDatos.ListaPrioridad;

public class Moderador extends Usuario {

    public Moderador(String nombre, String nickname, String contrasenia, String carrera) {
        super(nombre, nickname, contrasenia, carrera);
    }

    // Metodo para obtener el grafo de conexiones: COMPLETAR
    public GrafoNoDirigido obtenerGrafo() {
        return new GrafoNoDirigido();
    }

    // Metodo para eliminar una publicacion: COMPLETAR
    public void borrarPublicacion(Publicacion publicacion) {
        if (publicacion == null) {
            throw new IllegalArgumentException("Valor nulo al intentar borrar la publicación.");
        }
    }

    // Metodo para eliminar un estudiante: COMPLETAR
    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Valor nulo al intentar eliminar al estudiante.");
        }
    }

    // Metodo para eliminar un grupo: COMPLETAR
    public void eliminarGrupo(GrupoEstudio grupo) {
        if (grupo == null) {
            throw new IllegalArgumentException("Valor nulo al intentar eliminar el grupo.");
        }
    }

    // Metodo para Banear un estudiante
    public void banearEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Valor nulo al intentar banear al estudiante.");
        }
        estudiante.banear();
        System.out.println("El estudiante '" + estudiante.getNickname() + "' ha sido baneado.");
    }

    // Metodo para desbanear un estudiante
    public void desbanearEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Valor nulo al intentar desbanear al estudiante.");
        }
        estudiante.desbanear();
        System.out.println("El estudiante '" + estudiante.getNickname() + "' ha sido desbaneado.");
    }

    // Metodo para obtener publicaciones mejor valoradas: COMPLETAR
    public ListaPrioridad<Publicacion> obtenerPublicacionesValoradas() {

        ListaPrioridad<Publicacion> publicacionesValoradas = new ListaPrioridad<>();
        return publicacionesValoradas;
    }

    // Metodo para obtener los estudiantes con mas conexiones: COMPLETAR
    public ListaPrioridad<Usuario> obtenerUsuariosMasConexiones() {
        ListaPrioridad<Usuario> usuariosConMasConexiones = new ListaPrioridad<>();
        return usuariosConMasConexiones;
    }

    //Metodo para obtener los grupos con mas estudiantes: COMPLETAR
    public ListaPrioridad<GrupoEstudio> obtenerGruposMasParticipacion() {
        ListaPrioridad<GrupoEstudio> gruposMasParticipacion = new ListaPrioridad<GrupoEstudio>();
        return gruposMasParticipacion;
    }


}
