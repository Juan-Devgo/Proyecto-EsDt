package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public class UsuariosDAO implements DataAccessObject<Usuario> {
    private final Connection connection;

    public UsuariosDAO(Connection connection) throws SQLException {
        if(connection == null) {
            throw new NullPointerException("Error fatal en UsuariosDAO: La conexión no existe o no fue establecida " +
                    "correctamente.");
        }

        if(connection.isClosed()) {
            throw new RuntimeException("Error fatal en UsuariosDAO: La conexión está cerrada.");
        }

        this.connection = connection;
    }

    @Override
    public void insertar(Collection<Usuario> insertables) {
        String sqlInsertUsuario = "INSERT INTO (nombre, nickname, contrasenia, carrera) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), contrasenia = VALUES(contrasenia), " +
                "carrera = VALUES(carrera)";
        String sqlInsertIntereses = "INSERT INTO (nickname_propietario, interes) VALUES (?, ?)";
        String sqlInsertAmigos = "INSERT INTO (nickname1, nickname2) VALUES (?, ?)";
        String sqlInsertSeguidores = "INSERT INTO (nickname_propietario, nickname) VALUES (?,?)";
        String sqlInsertSeguidos = "INSERT INTO (nickname_propietario, nickname) VALUES (?, ?)";

        try(PreparedStatement stmtUsuario = connection.prepareStatement(sqlInsertUsuario);
            PreparedStatement stmtIntereses = connection.prepareStatement(sqlInsertIntereses);
            PreparedStatement stmtAmigos = connection.prepareStatement(sqlInsertAmigos);
            PreparedStatement stmtSeguidores = connection.prepareStatement(sqlInsertSeguidores);
            PreparedStatement stmtSeguidos = connection.prepareStatement(sqlInsertSeguidos)
        ) {

            HashSet<String> amigosInsertados = new HashSet<>();

            for(Usuario u: insertables) {
                stmtUsuario.setString(1, u.getNombre());
                stmtUsuario.setString(2, u.getNickname());
                stmtUsuario.setString(3, u.getContrasenia());
                stmtUsuario.setString(4, u.getCarrera());
                stmtUsuario.addBatch();

                for(Usuario a: u.getAmigos()) {
                    String amigo1 = u.getNickname();
                    String amigo2 = a.getNickname();
                    String amigos = amigo1.compareTo(amigo2) < 0 ? amigo1 + "|" + amigo2 : amigo2 + "|" + amigo1;

                    if(amigosInsertados.add(amigos)) {
                        stmtAmigos.setString(1, u.getNickname());
                        stmtAmigos.setString(2, a.getNickname());
                        stmtAmigos.addBatch();
                    }
                }

                for(String interes: u.getIntereses()) {
                    stmtIntereses.setString(1, u.getNickname());
                    stmtIntereses.setString(2, interes);
                    stmtIntereses.addBatch();
                }

                for(Usuario s: u.getSeguidores()) {
                    stmtSeguidores.setString(1, u.getNickname());
                    stmtSeguidores.setString(2, s.getNickname());
                    stmtSeguidores.addBatch();
                }

                for(Usuario s: u.getSeguidos()) {
                    stmtSeguidos.setString(1, u.getNickname());
                    stmtSeguidos.setString(2, s.getNickname());
                    stmtSeguidos.addBatch();
                }
            }

            stmtUsuario.executeBatch();
            stmtAmigos.executeBatch();
            stmtIntereses.executeBatch();
            stmtSeguidores.executeBatch();
            stmtSeguidos.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }
}