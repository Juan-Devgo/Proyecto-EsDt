package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import co.edu.uniquindio.proyectoesdt.Patrones.GruposEstudioDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.PublicacionesDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.UsuariosDAO;
import co.edu.uniquindio.proyectoesdt.util.Logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Plataforma {

    private static Plataforma instancia;
    public static final String nombre = "BrainLoop";
    private final GrafoNoDirigido<Usuario> usuarios;
    private final MiLinkedList<GrupoEstudio> gruposEstudio;
    private final ArbolBinarioABB<Publicacion> publicaciones;
    private final MiColaPrioridad<SolicitudAyuda> solicitudesAyuda;

    private final UsuariosDAO usuariosDAO;
    private final GruposEstudioDAO gruposEstudioDAO;
    private final PublicacionesDAO publicacionesDAO;

    public static Connection conectarBD() {
        Connection connection;
        String host = "jdbc:mysql://localhost:3306/";
        String usuario = "root";
        String contrasenia = "";
        String BD = "proyecto-esdt-BD";
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            connection = DriverManager.getConnection(host + BD, usuario, contrasenia);
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error de conexión con la base de datos...");
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void desconectarBD(Connection bd) {
        try {
            bd.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Plataforma() {
        this.usuarios = new GrafoNoDirigido<>();
        this.gruposEstudio = new MiLinkedList<>();
        this.publicaciones = new ArbolBinarioABB<>();
        this.solicitudesAyuda = new MiColaPrioridad<>();

        //----------------------- Conexión con la Base de Datos ------------------------//
        Connection connection = conectarBD();

        this.usuariosDAO = new UsuariosDAO(connection);
        this.gruposEstudioDAO = new GruposEstudioDAO(connection);
        this.publicacionesDAO = new PublicacionesDAO(connection);
        leerBD();
    }

    // Singleton
    public static Plataforma getInstancia() {
        if (instancia == null) {
            instancia = new Plataforma();
        }
        return instancia;
    }

    // Método para leer todos los archivos de la base de datos
    public void leerBD() {
        Collection<Usuario> usuariosBD = usuariosDAO.leer();
        for(Usuario u: usuariosBD) {
            usuarios.agregar(u);
        }

        for(Usuario u: usuarios) {
            for(Usuario seguidor: u.getSeguidores()) {
                if (!usuarios.existeConexion(u, seguidor)) {
                    usuarios.conectar(u, seguidor);
                }
            }
        }


        Collection<GrupoEstudio> gruposEstudioBD = gruposEstudioDAO.leer();
        for(GrupoEstudio g: gruposEstudioBD) {
            gruposEstudio.agregar(g);
        }

        Collection<Publicacion> publicacionesBD = publicacionesDAO.leer();
        for(Publicacion p: publicacionesBD) {
            publicaciones.agregar(p);
        }
    }

    // Método para generar grafo no dirigido
    public int[][] generarGrafo() {
        return usuarios.obtenerMatrizAdyacencia();
    }

    // Método para generar Grupos de Estudio: COMPLETAR
    public void generarGruposEstudio() {

    }

    // Método para banear un Estudiante
    public void banearEstudiante(Estudiante estudiante, Moderador moderador) {
        estudiante.banear();
        Logging.logInfo("El moderador " + moderador.nickname + " ha baneado al usuario " +
                estudiante.nickname +
                ".",this);
        usuariosDAO.insertar(List.of(estudiante));
    }

    // Método para desbanear un Estudiante
    public void desbanearEstudiante(Estudiante estudiante, Moderador moderador) {
        estudiante.desbanear();
        Logging.logInfo("El moderador " + moderador.nickname + " ha desbaneado al usuario " +
                estudiante.nickname +
                ".",this);
        usuariosDAO.insertar(List.of(estudiante));
    }

    // Método para validar si ya existe un nickname
    public boolean validarNicknameDisponible(String nickname){
        for (Usuario usuario : usuarios) {
            if (usuario.getNickname().equalsIgnoreCase(nickname.trim())) {
                return false;
            }
        }
        return true;
    }

    // Método para crear estudiante, usa validación de nickname
    public Estudiante crearEstudiante(String nombre, String nickname, String contrasenia, String carrera) {
        if (!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname.trim() + "' ya está en uso. Inténtalo con otro."
            );
        }
        Estudiante estudiante = new Estudiante(nombre, nickname.trim(), contrasenia, carrera);
        usuarios.agregar(estudiante);
        return estudiante;
    }

    // Método para crear una publicación, usa validación de nickname
    public Moderador crearModerador(String nombre, String nickname, String contrasenia, String carrera){
        if (!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname.trim() + "' ya está en uso. Inténtalo con otro."
            );
        }
        Moderador moderador = new Moderador(nombre, nickname.trim(), contrasenia, carrera);
        usuarios.agregar(moderador);
        return moderador;
    }

    // Método para eliminar un estudiante
    public void eliminarEstudiante(Estudiante estudiante){
        if(!usuarios.existe(estudiante)) {
            throw new IllegalArgumentException("Se ha intentado eliminar un estudiante nulo.");
        }
        for(Usuario u: estudiante.seguidores) {
            u.noSeguirUsuario(estudiante);
        }

        for(Usuario u: estudiante.seguidos) {
            estudiante.noSeguirUsuario(u);
        }

        for(GrupoEstudio g: gruposEstudio) {
            if(g.getSolicitudes().contains(estudiante)) {
                g.rechazarSolicitud(estudiante);
            }

            if(g.getIntegrantes().contains(estudiante)) {
                g.eliminarIntegrante(estudiante);
            }
        }

        ArrayList<Publicacion> publicacionesAux = new ArrayList<>();
        for(Publicacion p: publicaciones) {
            if(p.getAutor().nickname.equals(estudiante.getNickname())) {
                publicacionesAux.add(p);
            }
        }

        for(Publicacion p: publicacionesAux) {
            eliminarPublicacion(p);
        }
        publicacionesDAO.eliminar(publicacionesAux);

        usuarios.eliminar(estudiante);
        usuariosDAO.eliminar(List.of(estudiante));

        Collection<Usuario> actualizables = new ArrayList<>(estudiante.seguidores.stream().toList());
        actualizables.addAll(estudiante.seguidos.stream().toList());
        usuariosDAO.insertar(actualizables);
    }

    // Método para eliminar un moderador
    public void eliminarModerador(Moderador moderador){
        if(!usuarios.existe(moderador)) {
            throw new IllegalArgumentException("Se ha intentado eliminar un moderador nulo.");
        }

        for(Usuario u: moderador.seguidores) {
            u.noSeguirUsuario(moderador);
        }

        for(Usuario u: moderador.seguidos) {
            moderador.noSeguirUsuario(u);
        }

        ArrayList<Publicacion> publicacionesAux = new ArrayList<>();
        for(Publicacion p: publicaciones) {
            if(p.getAutor().nickname.equals(moderador.getNickname())) {
                publicacionesAux.add(p);
            }
        }

        for(Publicacion p: publicacionesAux) {
            eliminarPublicacion(p);
        }
        publicacionesDAO.eliminar(publicacionesAux);

        usuarios.eliminar(moderador);
        usuariosDAO.eliminar(List.of(moderador));

        Collection<Usuario> actualizables = new ArrayList<>(moderador.seguidores.stream().toList());
        actualizables.addAll(moderador.seguidos.stream().toList());
        usuariosDAO.insertar(actualizables);
    }

    // Método para actualizar atributos de un estudiante o moderador
    public void actualizarUsuario(Usuario usuario, String nombre, String contrasenia, String carrera){
        if(!nombre.equals(usuario.getNombre())){
            usuario.setNombre(nombre);
        }
        if(!contrasenia.equals(usuario.getContrasenia())){
            usuario.setContrasenia(contrasenia);
        }
        if(!carrera.equals(usuario.getCarrera())){
            usuario.setCarrera(carrera);
        }
        usuariosDAO.insertar(List.of(usuario));
    }

    // Método para actualizar el Nickname de un estudiante
    public void actualizarNicknameEstudiante(Estudiante estudiante, String nickname){
        if(!validarNicknameDisponible(nickname)){
            throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro."
            );
        }
        if(!nickname.equals(estudiante.getNickname())){
            Estudiante estudianteNuevo = new Estudiante(estudiante.nombre, nickname, estudiante.contrasenia,
                    estudiante.carrera);
            usuarios.agregar(estudianteNuevo);
            eliminarEstudiante(estudiante);
            usuariosDAO.eliminar(List.of(estudiante));
            usuariosDAO.insertar(List.of(estudianteNuevo));
        }
    }

    // Método para actualizar el Nickname de un moderador
    public void actualizarNicknameModerador(Moderador moderador, String nickname){
        if(!validarNicknameDisponible(nickname)){
            throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro."
            );
        }

        if(!nickname.equals(moderador.getNickname())){
            Moderador moderadorNuevo = new Moderador(moderador.nombre, nickname, moderador.contrasenia,
                    moderador.carrera);
            usuarios.agregar(moderadorNuevo);
            eliminarModerador(moderador);
            usuariosDAO.eliminar(List.of(moderador));
            usuariosDAO.insertar(List.of(moderadorNuevo));
        }
    }

    // Método para eliminar una publicación
    public void eliminarPublicacion(Publicacion publicacion){
        if(publicacion == null){
            throw  new IllegalArgumentException("No se puede eliminar una publicación nula.");
        }
        if (!publicaciones.existe(publicacion)){
            throw  new RuntimeException("No se puede eliminar una publicación no existente.");
        }

        publicaciones.eliminar(publicacion);
        publicacionesDAO.eliminar(List.of(publicacion));
    }

    // Método para eliminar un grupo de estudio
    public void eliminarGrupoEstudio(GrupoEstudio grupo){
        if( grupo == null){
            throw new IllegalArgumentException("No se puede eliminar un grupo de estudio nulo. ");
        }
        if(!gruposEstudio.contiene(grupo)){
            throw new IllegalArgumentException("No se puede eliminar un grupo de estudio no existente.");
        }

        gruposEstudio.eliminar(grupo);
        gruposEstudioDAO.eliminar(List.of(grupo));
    }

    // Método para buscar un estudiante
    public Estudiante buscarEstudiante(String nickname) {
        Estudiante estudianteEncontrado = null;

        if(nickname == null || nickname.isBlank()){
            throw new IllegalArgumentException(" No se puede encontrar un estudiante sin nickname.");
        }

        for(Usuario u: usuarios) {
            if(u instanceof Estudiante e) {
                if(e.nickname.equals(nickname)){
                    estudianteEncontrado = e;
                    break;
                }
            }
        }

        return estudianteEncontrado;
    }

    // Método para buscar un moderador
    public Moderador buscarModerador(String nickname) {
        Moderador moderadorEncontrado = null;

        if(nickname == null || nickname.isBlank()){
            throw new IllegalArgumentException(" No se puede encontrar un moderador con nickname nulo.");
        }

        for(Usuario u: usuarios) {
            if(u instanceof Moderador m) {
                if(m.nickname.equals(nickname)){
                    moderadorEncontrado = m;
                    break;
                }
            }
        }

        return moderadorEncontrado;
    }


    public GrafoNoDirigido<Usuario> getUsuarios() {
        return usuarios;
    }

    public MiLinkedList<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public ArbolBinarioABB<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public MiColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }
}