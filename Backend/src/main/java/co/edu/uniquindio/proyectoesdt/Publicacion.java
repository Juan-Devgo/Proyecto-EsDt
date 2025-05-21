package co.edu.uniquindio.proyectoesdt;

import java.util.HashMap;
import java.util.HashSet;

public class Publicacion implements InsertableBD, Comparable<Publicacion> {
    protected String titulo, tema;
    protected Usuario autor;
    protected TipoPublicacion tipoPublicacion;
    protected Contenido contenido;
    protected final HashSet<Usuario> likes;
    protected final HashMap<Usuario, String> comentarios;

    public Publicacion(String titulo, String tema, Usuario autor, TipoPublicacion tipoPublicacion, Contenido contenido){
        if (titulo == null || titulo.isBlank() || tema == null || tema.isBlank() || autor == null ||
                tipoPublicacion == null || contenido == null) {
            throw new IllegalArgumentException("Al menos uno de los datos suministrados es inválido o nulo al crear " +
                    "una publicación.");
        }
        if (tipoPublicacion == TipoPublicacion.SOLICITUD_AYUDA && getClass() == Publicacion.class) {
            throw new IllegalArgumentException("Las solicitudes de ayuda se deben realizar usando directamente la " +
                    "clase SolicitudAyuda");
        }
        this.titulo = titulo;
        this.tema = tema;
        this.autor = autor;
        this.likes = new HashSet<>();
        this.comentarios = new HashMap<>();
    }

    @Override
    public int compareTo(Publicacion o) {
        return titulo.compareTo(o.titulo);
    }

    public int getCantidadLikes() {
        return likes.size();
    }

    public void recibirLike(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al recibir like.");
        }

        likes.add(us);
    }

    public void perderLike(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al perder like.");
        }

        likes.remove(us);
    }

    public void recibirComentario(Usuario us, String comentario) {
        if (us == null || comentario == null) {
            throw new IllegalArgumentException("Valor nulo al recibir comentario.");
        }

        if (!comentario.isBlank()) {
            comentarios.put(us, comentario);
        }
    }

    public void perderComentario(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al perder comentario.");
        }

        comentarios.remove(us);
    }

    public void editarComentario(Usuario us, String comentario) {
        if (us == null || comentario == null) {
            throw new IllegalArgumentException("Valor nulo al editar comentario.");
        }

        comentarios.remove(us);
        comentarios.put(us, comentario);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public TipoPublicacion getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public HashSet<Usuario> getLikes() {
        return likes;
    }

    public HashMap<Usuario, String> getComentarios() {
        return comentarios;
    }
}
