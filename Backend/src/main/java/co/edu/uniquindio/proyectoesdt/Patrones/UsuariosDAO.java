package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class UsuariosDAO implements DataAccessObject<Usuario> {
    private Connection connection;

    public UsuariosDAO(Connection connection) {
        this.connection = null;

        try {
            if (connection == null) {
                throw new NullPointerException("Error fatal en UsuariosDAO: La conexión no existe o no fue establecida " +
                        "correctamente.");
            }

            if (connection.isClosed()) {
                throw new RuntimeException("Error fatal en UsuariosDAO: La conexión está cerrada.");
            }

            this.connection = connection;
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: " + e.getMessage());
        }
    }

    @Override
    public void insertar(Collection<Usuario> insertables) {
        String sqlInsertUsuario = "INSERT INTO usuarios (nombre, nickname, contrasenia, carrera, es_estudiante, " +
                "es_activo) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), contrasenia = " +
                "VALUES(contrasenia), carrera = VALUES(carrera), es_estudiante = VALUES(es_estudiante), es_activo = " +
                "VALUES(es_activo)";

        try(PreparedStatement stmtUsuario = connection.prepareStatement(sqlInsertUsuario)) {
            for(Usuario u: insertables) {
                stmtUsuario.setString(1, u.getNombre());
                stmtUsuario.setString(2, u.getNickname());
                stmtUsuario.setString(3, u.getContrasenia());
                stmtUsuario.setString(4, u.getCarrera());
                stmtUsuario.setBoolean(5, u.getClass() == Estudiante.class);
                stmtUsuario.setBoolean(6, u.getActivo());
                stmtUsuario.addBatch();

                actualizarSeguidores(u);
                actualizarIntereses(u);
            }

            stmtUsuario.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }

    @Override
    public Collection<Usuario> leer() {
        HashMap<String, Usuario> usuarios = new HashMap<>();

        String sqlSelectUsuarios = "SELECT * FROM usuarios";
        String sqlSelectIntereses = "SELECT * FROM intereses";
        String sqlSelectSeguidores = "SELECT * FROM seguidores";

        try(PreparedStatement stmtUsuarios = connection.prepareStatement(sqlSelectUsuarios);
            PreparedStatement stmtIntereses = connection.prepareStatement(sqlSelectIntereses);
            PreparedStatement stmtSeguidores = connection.prepareStatement(sqlSelectSeguidores)
        ) {
            ResultSet rsUsuarios = stmtUsuarios.executeQuery();
            while(rsUsuarios.next()) {
                String nombre = rsUsuarios.getString("nombre");
                String nickname = rsUsuarios.getString("nickname");
                String contrasenia = rsUsuarios.getString("contrasenia");
                String carrera = rsUsuarios.getString("carrera");

                if(rsUsuarios.getBoolean("es_estudiante")) {
                    Estudiante e = new Estudiante(nombre,nickname, contrasenia, carrera);
                    if(!rsUsuarios.getBoolean("es_activo")) {
                        e.banear();
                    }
                    usuarios.put(nickname, e);
                } else {
                    Moderador m = new Moderador(nombre, nickname, contrasenia, carrera);
                    usuarios.put(nickname, m);
                }
            }

            ResultSet rsIntereses = stmtIntereses.executeQuery();
            while(rsIntereses.next()) {
                String nickname = rsIntereses.getString("nickname_propietario");
                String interes = rsIntereses.getString("interes");

                usuarios.get(nickname).agregarInteres(interes);
            }

            ResultSet rsSeguidores = stmtSeguidores.executeQuery();
            while(rsSeguidores.next()) {
                String nickname_propietario = rsSeguidores.getString("nickname_propietario");
                String nickname = rsSeguidores.getString("nickname");

                usuarios.get(nickname).seguirUsuario(usuarios.get(nickname_propietario));
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }

        return usuarios.values();
    }

    @Override
    public void eliminar(Collection<Usuario> eliminables) {
        String sqlDeleteUsuarios = "DELETE FROM usuarios WHERE nickname = ?";
        String sqlDeleteIntereses = "DELETE FROM intereses WHERE nickname_propietario = ?";

        try(PreparedStatement stmtDeleteUsuarios = connection.prepareStatement(sqlDeleteUsuarios);
            PreparedStatement stmtDeleteIntereses = connection.prepareStatement(sqlDeleteIntereses)
        ) {
            for(Usuario u: eliminables) {
                stmtDeleteUsuarios.setString(1, u.getNickname());
                stmtDeleteUsuarios.executeUpdate();

                stmtDeleteIntereses.setString(1, u.getNickname());
                stmtDeleteIntereses.executeUpdate();
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarIntereses(Usuario u) {
        String sqlDeleteIntereses = "DELETE FROM intereses WHERE nickname_propietario = ?";
        String sqlInsertIntereses = "INSERT INTO intereses (nickname_propietario, interes) VALUES (?, ?)";

        try(PreparedStatement stmtInsertIntereses = connection.prepareStatement(sqlInsertIntereses);
            PreparedStatement stmtDeleteIntereses = connection.prepareStatement(sqlDeleteIntereses)
        ) {
            stmtDeleteIntereses.setString(1, u.getNickname());
            stmtDeleteIntereses.executeUpdate();

            for(String interes: u.getIntereses()) {
                stmtInsertIntereses.setString(1, u.getNickname());
                stmtInsertIntereses.setString(2, interes);
                stmtInsertIntereses.addBatch();
            }

            stmtInsertIntereses.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarSeguidores(Usuario u) {
        String sqlDeleteSeguidores = "DELETE FROM seguidores WHERE nickname_propietario = ?";
        String sqlInsertSeguidores = "INSERT INTO seguidores (nickname_propietario, nickname) VALUES (?, ?)";

        try (PreparedStatement stmtDeleteSeguidores = connection.prepareStatement(sqlDeleteSeguidores);
             PreparedStatement stmtInsertSeguidores = connection.prepareStatement(sqlInsertSeguidores)
        ){
            stmtDeleteSeguidores.setString(1, u.getNickname());
            stmtDeleteSeguidores.executeUpdate();

            for(Usuario s: u.getSeguidores()) {
                stmtInsertSeguidores.setString(1, u.getNickname());
                stmtInsertSeguidores.setString(2, s.getNickname());
                stmtInsertSeguidores.addBatch();
            }

            stmtInsertSeguidores.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }

    public boolean interesExiste(String nickname_propietario, String interes) throws SQLException {
        String sql = "SELECT 1 FROM intereses WHERE nickname_propietario = ? AND interes = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nickname_propietario);
            stmt.setString(2, interes);

            return stmt.executeQuery().next();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }

    public boolean seguidorExiste(String nickname_propietario, String nickname) throws SQLException {
        String sql = "SELECT 1 FROM seguidores WHERE nickname_propietario = ? AND nickname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nickname_propietario);
            stmt.setString(2, nickname);

            return stmt.executeQuery().next();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en UsuariosDAO: Imposible crear PreparedStatement.");
        }
    }
}