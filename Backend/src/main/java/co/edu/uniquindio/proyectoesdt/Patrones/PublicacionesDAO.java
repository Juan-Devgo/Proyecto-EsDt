package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Publicacion;
import co.edu.uniquindio.proyectoesdt.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class PublicacionesDAO implements DataAccessObject<Publicacion>{
    private Connection connection;

    public PublicacionesDAO(Connection connection) {

        this.connection = null;

        try{

            if(connection == null) {
                throw new NullPointerException("Error fatal en PublicacionesDAO: La conexión no existe o no fue establecida " +
                        "correctamente.");
            }

            if(connection.isClosed()) {
                throw new RuntimeException("Error fatal en PublicacionesDAO: La conexión está cerrada.");
            }

            this.connection = connection;

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: " + e.getMessage());
        }
    }

    @Override
    public void insertar(Collection<Publicacion> insertables) {
        String sqlInsertPublicacion = "INSERT INTO (titulo, tema, nickname_autor, tipo) VALUES (?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE tema = VALUES(tema), nickname_autor = VALUES(nickname_autor), " +
                "tipo = VALUES(tipo)";
        String sqlInsertLikes = "INSERT INTO (titulo_publicacion_propietario, nickname) VALUES (?, ?)";
        String sqlInsertComentarios = "INSERT INTO (titulo_publicacion_propietario, nickname, comentario) VALUES " +
                "(?, ?, ?)";

        try(PreparedStatement stmtPublicacion = connection.prepareStatement(sqlInsertPublicacion);
            PreparedStatement stmtLikes = connection.prepareStatement(sqlInsertLikes);
            PreparedStatement stmtComentarios = connection.prepareStatement(sqlInsertComentarios)
        ) {

            for(Publicacion p: insertables) {
                stmtPublicacion.setString(1, p.getTitulo());
                stmtPublicacion.setString(2, p.getTema());
                stmtPublicacion.setString(3, p.getAutor().getNickname());
                stmtPublicacion.setString(4, p.getTipoPublicacion().toString());
                stmtPublicacion.addBatch();

                for(Usuario l: p.getLikes()) {
                    stmtLikes.setString(1, p.getTitulo());
                    stmtLikes.setString(2, l.getNickname());
                    stmtLikes.addBatch();
                }

                for(Usuario c: p.getComentarios().keySet()) {
                    stmtComentarios.setString(1, p.getTitulo());
                    stmtComentarios.setString(2, c.getNickname());
                    stmtComentarios.setString(3, p.getComentarios().get(c));
                    stmtComentarios.addBatch();
                }
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        }
    }

    @Override
    public Collection<Publicacion> leer() {
        return List.of();
    }
}