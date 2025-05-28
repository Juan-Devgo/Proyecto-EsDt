package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.*;
import co.edu.uniquindio.proyectoesdt.util.Archivos;
import co.edu.uniquindio.proyectoesdt.util.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.*;

public class PublicacionesDAO implements DataAccessObject<Publicacion>{
    private Connection connection;
    private final Plataforma plataforma;

    public PublicacionesDAO(Connection connection, Plataforma plataforma) {
        this.connection = null;
        this.plataforma = plataforma;

        Logging.logInfo("Intentando crear PublicacionesDAO...", this);

        try{
            if(connection == null) {
                throw new NullPointerException("Error fatal en PublicacionesDAO: La conexión no existe o no fue establecida " +
                        "correctamente.");
            }

            if(connection.isClosed()) {
                throw new RuntimeException("Error fatal en PublicacionesDAO: La conexión está cerrada.");
            }

            this.connection = connection;

            Logging.logInfo("PublicacionesDAO creado con éxito", this);

        } catch (SQLException e) {
            e.fillInStackTrace();
            Logging.logSevere("Error fatal en PublicacionesDAO: " + e.getMessage(), this);
        }
    }

    @Override
    public void insertar(Collection<Publicacion> insertables) {
        String sqlInsertPublicacion = "INSERT INTO publicaciones (titulo, tema, nickname_autor, tipo, parrafos, " +
                "activa, prioridad) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "tema = VALUES(tema), " +
                "nickname_autor = VALUES(nickname_autor), " +
                "tipo = VALUES(tipo), " +
                "parrafos = VALUES(parrafos), " +
                "activa = VALUES(activa), " +
                "prioridad = VALUES(prioridad);";

        try(PreparedStatement stmtPublicacion = connection.prepareStatement(sqlInsertPublicacion)) {

            for(Publicacion p: insertables) {

                Logging.logInfo("Insertando la publicación: " + p.getTitulo() + "...", this);

                stmtPublicacion.setString(1, p.getTitulo());
                stmtPublicacion.setString(2, p.getTema());
                stmtPublicacion.setString(3, p.getAutor().getNickname());
                stmtPublicacion.setString(4, p.getTipoPublicacion().toString());

                File parrafos = p.getContenido().getParrafosFile();

                stmtPublicacion.setBinaryStream(5, new FileInputStream(parrafos), (int) parrafos.length());

                if(p instanceof SolicitudAyuda sa) {
                    stmtPublicacion.setBoolean(6, sa.isActiva());
                    stmtPublicacion.setString(7, sa.getPrioridad().toString());
                } else {
                    stmtPublicacion.setBoolean(6, true);
                    stmtPublicacion.setString(7, Prioridad.BAJA.toString());
                }

                stmtPublicacion.addBatch();

                actualizarLikes(p);
                actualizarComentarios(p);
                actualizarArchivos(p);

                Logging.logInfo("La publicación: " + p.getTitulo() + " se ha insertado correctamente.",
                        this);
            }

            stmtPublicacion.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        } catch (IOException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Input/Output error: " + e.getMessage());
        }
    }

    @Override
    public Collection<Publicacion> leer() {
        HashMap<String, Publicacion> publicaciones = new HashMap<>();

        String sqlSelectPublicaciones = "SELECT * FROM publicaciones";
        String sqlSelectArchivos = "SELECT * FROM archivos WHERE titulo_publicacion_propietario = ?";

        try(PreparedStatement stmtSelectPublicaciones = connection.prepareStatement(sqlSelectPublicaciones);
            PreparedStatement stmtSelectArchivos = connection.prepareStatement(sqlSelectArchivos)
        ){
            ResultSet rsPublicaciones = stmtSelectPublicaciones.executeQuery();
            while (rsPublicaciones.next()) {

                Logging.logInfo("Leyendo publicación...", this);

                String titulo = rsPublicaciones.getString("titulo");
                String tema = rsPublicaciones.getString("tema");
                String nicknameAutor = rsPublicaciones.getString("nickname_autor");
                String tipoString = rsPublicaciones.getString("tipo");

                Usuario autor = plataforma.buscarEstudiante(nicknameAutor);
                Contenido contenido = new Contenido(titulo);

                if(autor == null) {
                    autor = plataforma.buscarModerador(nicknameAutor);
                }

                Publicacion p;
                TipoPublicacion tipo = TipoPublicacion.valueOf(tipoString);

                if(tipo != TipoPublicacion.SOLICITUD_AYUDA) {
                    p = new Publicacion(titulo, tema, autor, tipo, contenido);
                } else {
                    String prioridad = rsPublicaciones.getString("prioridad");
                    boolean activa = rsPublicaciones.getBoolean("activa");
                    p = new SolicitudAyuda(titulo, tema, autor, contenido, activa, Prioridad.valueOf(prioridad));
                }

                publicaciones.put(titulo, p);

                //Leer archivos

                String tituloSeguro = Archivos.limpiarNombreArchivo(titulo.trim());

                //Leer archivo de párrafos
                InputStream parrafosStream = rsPublicaciones.getBinaryStream("parrafos");
                Path parrafosPath = Path.of(Archivos.obtenerRutaFiles(), tituloSeguro + "_parrafos.txt");
                Files.copy(parrafosStream, parrafosPath, StandardCopyOption.REPLACE_EXISTING);
                contenido.setParrafos(parrafosPath.toFile());

                //Leer archivos adjuntos
                stmtSelectArchivos.setString(1, titulo);
                ResultSet rsArchivos = stmtSelectArchivos.executeQuery();
                while(rsArchivos.next()) {
                    int posicion = rsArchivos.getInt("posicion");
                    InputStream archivoStream = rsArchivos.getBinaryStream("archivo");

                    Path archivoPath = Path.of(Archivos.obtenerRutaFiles(), tituloSeguro + "_archivo" + posicion);
                    Files.copy(archivoStream, archivoPath, StandardCopyOption.REPLACE_EXISTING);
                    contenido.agregarArchivo(posicion, archivoPath.toFile());
                }

                Logging.logInfo("Se ha leído la publicación: " + titulo + ".", this);
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        } catch (IOException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Input/Output error: " + e.getMessage());
        }

        return publicaciones.values();
    }

    @Override
    public void eliminar(Collection<Publicacion> eliminables) {
        String sqlDeletePublicaciones = "DELETE FROM publicaciones WHERE titulo = ?";
        String sqlDeleteLikes = "DELETE FROM likes WHERE titulo_publicacion_propietario = ?";
        String sqlDeleteComentarios = "DELETE FROM comentarios WHERE titulo_publicacion_propietario = ?";
        String sqlDeleteArchivos = "DELETE FROM archivos WHERE titulo_publicacion_propietario = ?";

        try(PreparedStatement stmtPublicaciones = connection.prepareStatement(sqlDeletePublicaciones);
            PreparedStatement stmtLikes = connection.prepareStatement(sqlDeleteLikes);
            PreparedStatement stmtComentarios = connection.prepareStatement(sqlDeleteComentarios);
            PreparedStatement stmtArchivos = connection.prepareStatement(sqlDeleteArchivos)
        ){
            for(Publicacion p: eliminables) {

                Logging.logInfo("Eliminando la publicación: " + p.getTitulo() + "...", this);

                stmtPublicaciones.setString(1, p.getTitulo());
                stmtPublicaciones.executeUpdate();

                stmtLikes.setString(1, p.getTitulo());
                stmtLikes.executeUpdate();

                stmtComentarios.setString(1, p.getTitulo());
                stmtComentarios.executeUpdate();

                stmtArchivos.setString(1, p.getTitulo());
                stmtArchivos.executeUpdate();

                File files = new File("files");
                if(files.exists() && files.isDirectory()) {
                    File[] archivos = files.listFiles();
                    if(archivos != null) {
                        for (File archivo: archivos) {
                            String archivoNombre = archivo.getName();
                            if(archivo.isFile() && archivoNombre.contains(p.getTitulo().trim())){
                                if(archivo.delete()){
                                    Logging.logInfo("Archivo eliminado: " + archivoNombre, this);
                                } else {
                                    Logging.logWarning("El archivo no se pudo eliminar: " + archivoNombre,
                                            this);
                                }
                            }
                        }
                    }
                }

                Logging.logInfo("Publicación eliminada.", this);
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarLikes(Publicacion p) {
        String sqlDeleteLikes = "DELETE FROM likes WHERE titulo_publicacion_propietario = ?";
        String sqlInsertLikes = "INSERT INTO likes (titulo_publicacion_propietario, nickname) VALUES (?, ?)";

        try(PreparedStatement stmtDeleteLikes = connection.prepareStatement(sqlDeleteLikes);
            PreparedStatement stmtInsertLikes = connection.prepareStatement(sqlInsertLikes)
        ) {
            stmtDeleteLikes.setString(1, p.getTitulo());
            stmtDeleteLikes.executeUpdate();

            for(Usuario l: p.getLikes()) {
                stmtInsertLikes.setString(1, p.getTitulo());
                stmtInsertLikes.setString(2, l.getNickname());
                stmtInsertLikes.addBatch();
            }

            stmtInsertLikes.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarComentarios(Publicacion p) {
        String sqlDeleteComentarios = "DELETE FROM comentarios WHERE titulo_publicacion_propietario = ?";
        String sqlInsertComentarios = "INSERT INTO comentarios (titulo_publicacion_propietario, nickname, comentario) "+
                "VALUES (?, ?, ?)";

        try(PreparedStatement stmtDeleteComentarios = connection.prepareStatement(sqlDeleteComentarios);
            PreparedStatement stmtInsertComentarios = connection.prepareStatement(sqlInsertComentarios)
        ){
            stmtDeleteComentarios.setString(1, p.getTitulo());
            stmtDeleteComentarios.executeUpdate();

            for(Usuario c: p.getComentarios().keySet()) {
                stmtInsertComentarios.setString(1, p.getTitulo());
                stmtInsertComentarios.setString(2, c.getNickname());
                stmtInsertComentarios.setString(3, p.getComentarios().get(c));
                stmtInsertComentarios.addBatch();
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        }
    }

    public void actualizarArchivos(Publicacion p) {
        String sqlDeleteArchivos = "DELETE FROM archivos WHERE titulo_publicacion_propietario = ?";
        String sqlInsertArchivos = "INSERT INTO archivos (titulo_publicacion_propietario, archivo, posicion) " +
                "VALUES (?, ?, ?)";

        try(PreparedStatement stmtDeleteArchivos = connection.prepareStatement(sqlDeleteArchivos);
            PreparedStatement stmtInsertArchivos = connection.prepareStatement(sqlInsertArchivos)
        ) {
            stmtDeleteArchivos.setString(1, p.getTitulo());
            stmtDeleteArchivos.executeUpdate();

            ArrayList<File> archivos = p.getContenido().getArchivos();
            for(File archivo: archivos) {
                stmtInsertArchivos.setString(1, p.getTitulo());
                stmtInsertArchivos.setBinaryStream(2, new FileInputStream(archivo), (int) archivo
                        .length());
                stmtInsertArchivos.setInt(3, archivos.indexOf(archivo));
                stmtInsertArchivos.addBatch();
            }

            stmtInsertArchivos.executeBatch();

        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Imposible crear PreparedStatement.");
        } catch (IOException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error fatal en PublicacionesDAO: Input/Output error: " + e.getMessage());
        }
    }
}