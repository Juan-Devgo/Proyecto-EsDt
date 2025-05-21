package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Publicacion;
import co.edu.uniquindio.proyectoesdt.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class PublicacionDAO implements DataAccessObject<Publicacion>{
    private final Connection connection;

    public PublicacionDAO(Connection connection) throws SQLException {
        if(connection == null) {
            throw new NullPointerException("Error fatal en PublicacionDAO: La conexión no existe o no fue establecida " +
                    "correctamente.");
        }

        if(connection.isClosed()) {
            throw new RuntimeException("Error fatal en PublicacionDAO: La conexión está cerrada.");
        }

        this.connection = connection;
    }

    @Override
    public void insertar(Collection<Publicacion> insertables) {
        String sqlInsertPublicacion = "INSERT INTO (titulo, tema, nickname_autor, tipo) VALUES (?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE tema = VALUES(tema), nickname_autor = VALUES(nickname_autor), " +
                "tipo = VALUES(tipo)";
        String sqlInsertLikes = "INSERT INTO (titulo, nickname_like) VALUES (?, ?)";
        String sqlInsertComentarios = "INSERT INTO (titulo, nickname, comentario) VALUES (?, ?, ?)";

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
            throw new RuntimeException("Error fatal en PublicacionDAO: Imposible crear PreparedStatement.");
        }
    }
}