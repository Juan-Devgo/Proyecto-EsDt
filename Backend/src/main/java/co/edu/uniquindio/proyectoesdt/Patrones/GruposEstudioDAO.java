package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.GrupoEstudio;
import co.edu.uniquindio.proyectoesdt.Plataforma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GruposEstudioDAO implements DataAccessObject<GrupoEstudio> {
    private Connection connection;

    public GruposEstudioDAO(Connection connection) {

        this.connection = null;

        try{
            if(connection == null) {
                throw new NullPointerException("Error fatal en GruposEstudioDAO: La conexión no existe o no fue " +
                        "establecida correctamente.");
            }

            if(connection.isClosed()) {
                throw new RuntimeException("Error fatal en GruposEstudioDAO: La conexión está cerrada.");
            }

            this.connection = connection;

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: " + e.getMessage());
        }
    }

    @Override
    public void insertar(Collection<GrupoEstudio> insertables) {
        String sqlInsertNombre = "INSERT IGNORE INTO grupos_estudio (nombre) VALUES (?)";

        try(PreparedStatement stmtNombre = connection.prepareStatement(sqlInsertNombre)) {

            for(GrupoEstudio g: insertables) {
                stmtNombre.setString(1, g.getNombre());
                stmtNombre.addBatch();

                actualizartemas(g);
                actualizarSolicitudes(g);
                actualizarIntegrantes(g);
            }

            stmtNombre.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }

    @Override
    public Collection<GrupoEstudio> leer() {
        HashMap<String, GrupoEstudio> gruposEstudio = new HashMap<>();

        String sqlSelectGruposEstudio = "SELECT * FROM grupos_estudio";
        String sqlSelectTemas = "SELECT * FROM temas";
        String sqlSelectSolicitudes = "SELECT * FROM solicitudes";
        String sqlSelectIntegrantes = "SELECT * FROM integrantes";

        try(PreparedStatement stmtSelectGruposEstudio = connection.prepareStatement(sqlSelectGruposEstudio);
            PreparedStatement stmtSelectTemas = connection.prepareStatement(sqlSelectTemas);
            PreparedStatement stmtSelectSolicitudes = connection.prepareStatement(sqlSelectSolicitudes);
            PreparedStatement stmtSelectIntegrantes = connection.prepareStatement(sqlSelectIntegrantes)
        ) {
            ResultSet rsGruposEstudio = stmtSelectGruposEstudio.executeQuery();
            while(rsGruposEstudio.next()) {
                String nombre = rsGruposEstudio.getString("nombre");
                GrupoEstudio g = new GrupoEstudio(nombre);
                gruposEstudio.put(nombre, g);
            }

            ResultSet rsTemas = stmtSelectTemas.executeQuery();
            while (rsTemas.next()) {
                String nombre = rsTemas.getString("nombre_grupo_propietario");
                String tema = rsTemas.getString("tema");
                gruposEstudio.get(nombre).agregarTema(tema);
            }

            ResultSet rsSolicitudes = stmtSelectSolicitudes.executeQuery();
            while(rsSolicitudes.next()) {
                String nombre = rsSolicitudes.getString("nombre_grupo_propietario");
                Estudiante e = Plataforma.getInstancia().buscarEstudiante(rsSolicitudes
                        .getString("nickname"));
                if(e != null) {
                    gruposEstudio.get(nombre).recibirSolicitud(e);
                }
            }

            ResultSet rsIntegrantes = stmtSelectIntegrantes.executeQuery();
            while(rsIntegrantes.next()) {
                String nombre = rsIntegrantes.getString("nombre_grupo_propietario");
                Estudiante e = Plataforma.getInstancia().buscarEstudiante(rsIntegrantes
                        .getString("nickname"));
                if(e != null) {
                    gruposEstudio.get(nombre).aceptarSolicitud(e);
                }
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }

        return gruposEstudio.values();
    }

    @Override
    public void eliminar(Collection<GrupoEstudio> eliminables) {
        String sqlDeleteGrupos = "DELETE FROM grupos_estudio WHERE nombre = ?";
        String sqlDeleteTemas = "DELETE FROM temas WHERE nombre_grupo_propietario = ?";
        String sqlDeleteSolicitudes = "DELETE FROM solicitudes WHERE nombre_grupo_propietario = ?";
        String sqlDeleteIntegrantes = "DELETE FROM integrantes WHERE nombre_grupo_propietario = ?";

        try(PreparedStatement stmtGrupos = connection.prepareStatement(sqlDeleteGrupos);
            PreparedStatement stmtTemas = connection.prepareStatement(sqlDeleteTemas);
            PreparedStatement stmtSolicitudes = connection.prepareStatement(sqlDeleteSolicitudes);
            PreparedStatement stmtIntegrantes = connection.prepareStatement(sqlDeleteIntegrantes)
        ){
            for(GrupoEstudio g: eliminables) {
                stmtGrupos.setString(1, g.getNombre());
                stmtGrupos.executeUpdate();

                stmtTemas.setString(1, g.getNombre());
                stmtTemas.executeUpdate();

                stmtSolicitudes.setString(1, g.getNombre());
                stmtSolicitudes.executeUpdate();

                stmtIntegrantes.setString(1, g.getNombre());
                stmtIntegrantes.executeUpdate();
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizartemas(GrupoEstudio g) {
        String sqlDeleteTemas = "DELETE FROM temas WHERE nombre_grupo_propietario = ?";
        String sqlInsertTemas = "INSERT INTO temas (nombre_grupo_propietario, tema) VALUES (?, ?)";

        try(PreparedStatement stmtDeleteTemas = connection.prepareStatement(sqlDeleteTemas);
            PreparedStatement stmtInsertTemas = connection.prepareStatement(sqlInsertTemas)
        ){
            stmtDeleteTemas.setString(1, g.getNombre());
            stmtDeleteTemas.executeUpdate();

            for(String t: g.getTemas()) {
                stmtInsertTemas.setString(1, g.getNombre());
                stmtInsertTemas.setString(2, t);
                stmtInsertTemas.addBatch();
            }

            stmtInsertTemas.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarSolicitudes(GrupoEstudio g) {
        String sqlDeleteSolicitudes = "DELETE FROM solicitudes WHERE nombre_grupo_propietario = ?";
        String sqlInsertSolicitudes = "INSERT INTO solicitudes (nombre_grupo_propietario, nickname) VALUES (?, ?)";

        try(PreparedStatement stmtDeleteSolicitudes = connection.prepareStatement(sqlDeleteSolicitudes);
            PreparedStatement stmtInsertSolicitudes = connection.prepareStatement(sqlInsertSolicitudes)
        ){
            stmtDeleteSolicitudes.setString(1, g.getNombre());
            stmtDeleteSolicitudes.executeUpdate();

            for(Estudiante e: g.getSolicitudes()) {
                stmtInsertSolicitudes.setString(1, g.getNombre());
                stmtInsertSolicitudes.setString(2, e.getNickname());
                stmtInsertSolicitudes.addBatch();
            }

            stmtInsertSolicitudes.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarIntegrantes(GrupoEstudio g) {
        String sqlDeleteIntegrantes = "DELETE FROM integrantes WHERE nombre_grupo_propietario = ?";
        String sqlInsertIntegrantes = "INSERT INTO integrantes (nombre_grupo_propietario, nickname) VALUES (?, ?)";

        try(PreparedStatement stmtDeleteIntegrantes = connection.prepareStatement(sqlDeleteIntegrantes);
            PreparedStatement stmtInsertIntegrantes = connection.prepareStatement(sqlInsertIntegrantes)
        ){
            stmtDeleteIntegrantes.setString(1, g.getNombre());
            stmtInsertIntegrantes.executeUpdate();

            for(Estudiante e: g.getIntegrantes()) {
                stmtInsertIntegrantes.setString(1, g.getNombre());
                stmtInsertIntegrantes.setString(2, e.getNickname());
                stmtInsertIntegrantes.addBatch();
            }

            stmtInsertIntegrantes.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en GruposEstudioDAO: Imposible crear PreparedStatement.");
        }
    }
}