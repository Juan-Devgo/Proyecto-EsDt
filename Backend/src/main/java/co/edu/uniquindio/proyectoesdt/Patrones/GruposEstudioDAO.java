package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.GrupoEstudio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class GruposEstudioDAO implements DataAccessObject<GrupoEstudio> {
    private final Connection connection;

    public GruposEstudioDAO(Connection connection) throws SQLException {
        if(connection == null) {
            throw new NullPointerException("Error fatal en GruposEstudioDAO: La conexión no existe o no fue establecida " +
                    "correctamente.");
        }

        if(connection.isClosed()) {
            throw new RuntimeException("Error fatal en GruposEstudioDAO: La conexión está cerrada.");
        }

        this.connection = connection;
    }

    @Override
    public void insertar(Collection<GrupoEstudio> insertables) {
        String sqlInsertNombre = "INSERT IGNORE INTO (nombre) VALUES (?)";
        String sqlInsertSolicitudes = "INSERT INTO (nombre_grupo_propietario, nickname) VALUES (?, ?)";
        String sqlInsertIntegrantes = "INSERT INTO (nombre_grupo_propietario, nickname) VALUES (?, ?)";

        try(PreparedStatement stmtNombre = connection.prepareStatement(sqlInsertNombre);
            PreparedStatement stmtSolicitudes = connection.prepareStatement(sqlInsertSolicitudes);
            PreparedStatement stmtIntegrantes = connection.prepareStatement(sqlInsertIntegrantes)
        ) {

            for(GrupoEstudio g: insertables) {
                stmtNombre.setString(1, g.getNombre());
                stmtNombre.addBatch();

                for(Estudiante e: g.getSolicitudes()) {
                    stmtSolicitudes.setString(1, g.getNombre());
                    stmtSolicitudes.setString(2, e.getNickname());
                    stmtSolicitudes.addBatch();
                }

                for(Estudiante e: g.getIntegrantes()) {
                    stmtIntegrantes.setString(1, g.getNombre());
                    stmtIntegrantes.setString(2, e.getNickname());
                    stmtIntegrantes.addBatch();
                }
            }

            stmtNombre.executeBatch();
            stmtSolicitudes.executeBatch();
            stmtIntegrantes.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }
}