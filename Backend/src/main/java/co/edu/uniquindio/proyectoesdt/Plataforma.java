package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import co.edu.uniquindio.proyectoesdt.Patrones.GruposEstudioDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.PublicacionesDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.UsuariosDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Plataforma {

    private static Plataforma instancia;
    public static final String nombre = "BrainLoop";
    private final GrafoNoDirigido<Usuario> usuarios;
    private final MiLinkedList<GrupoEstudio> gruposEstudio;
    private final ArbolBinarioABB<Publicacion> publicaciones;
    private final MiColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private final ArrayList<Usuario> representantesComunidades;

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
        this.representantesComunidades = new ArrayList<>();

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

    // Método para generar grupos de estudio
    public void generarGruposEstudio() {
        for(String interes: obtenerTodosIntereses()) {
            boolean existeGrupoInteres = false;
            for(GrupoEstudio g: gruposEstudio) {
                if(g.getTemas().contains(interes)) {
                    existeGrupoInteres = true;
                    break;
                }
            }

            if(!existeGrupoInteres) {
                crearGrupoEstudio("Grupo de " + interes);
            }
        }
    }

    // Método para detectar clústeres y crear comunidades
    public void crearComunidades() {
        for(Usuario usuario: usuarios) {
            if(!representantesComunidades.contains(usuario)) {
                for (Usuario representante : representantesComunidades) {
                    if (!usuarios.existeCamino(representante, usuario)) {
                        crearGrupoEstudio("Comunidad de " + usuario.getNickname());
                        representantesComunidades.add(usuario);
                    }
                }
            }
        }
    }

    //Método para generar conexiones entre estudiantes
    public void generarConexionesEstudiantes() {
        for(Usuario u: usuarios){
            if(u instanceof Estudiante estudiante) {
                for(Usuario seguidorUsuario: estudiante.getSeguidores()) {
                    if(seguidorUsuario instanceof Estudiante seguidorEstudiante) {
                        for(Usuario seguidorDeSeguidorUsuario: seguidorEstudiante.getSeguidores()) {
                            if(seguidorDeSeguidorUsuario instanceof Estudiante seguidorDeSeguidorEstudiante) {

                                if (!seguidorDeSeguidorEstudiante.equals(estudiante)) {

                                    boolean compartenIntereses = false;
                                    for(String interes: seguidorDeSeguidorEstudiante.getIntereses()) {
                                        if(estudiante.getIntereses().contains(interes)) {
                                            compartenIntereses = true;
                                            break;
                                        }
                                    }

                                    if(!usuarios.existeConexion(estudiante, seguidorDeSeguidorEstudiante) &&
                                            compartenIntereses) {
                                        usuarios.conectar(estudiante, seguidorDeSeguidorEstudiante);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> obtenerTodosIntereses() {
        ArrayList<String> intereses = new ArrayList<>();
        for(Usuario u: usuarios) {
            intereses.addAll(u.getIntereses());
            HashSet<String> set = new HashSet<>(intereses);
            intereses = new ArrayList<>(set);
        }
        return intereses;
    }

    public String obtenerCaminoMasCorto (Usuario usuario1, Usuario usuario2){
        String camino = usuarios.encontrarCaminoCorto(usuario1,usuario2);
        return camino;
    }

    public LinkedList<GrupoEstudio> obtenerGruposMasValorados(){
        PriorityQueue<GrupoEstudio> grupos = new PriorityQueue<>();
        LinkedList<GrupoEstudio> grupoEstudioMasValorados = new LinkedList<>();
        for(GrupoEstudio g: gruposEstudio) {
            grupos.add(g);
        }

        for(int i = 0; i < 5; i++) {
            grupoEstudioMasValorados.add(grupos.poll());
        }

        return grupoEstudioMasValorados;
    }

    public LinkedList<Usuario> obtenerUsuariosMasConexiones(){
        PriorityQueue<Usuario> usuariosC = new PriorityQueue<>();
        LinkedList<Usuario> usuariosMasConexiones = new LinkedList<>();

        for(Usuario g: usuarios ){
            usuariosC.add(g);
        }

        for(int i = 0; i < 5 ; i++) {
            usuariosMasConexiones.add(usuariosC.poll());
        }

        return usuariosMasConexiones;
    }

    public LinkedList<Publicacion> obtenerPublicacionesMasValoradas(){
        PriorityQueue<Publicacion> publicacionesV = new PriorityQueue<>();
        LinkedList<Publicacion> publicacionesMasValoradas = new LinkedList<>();

        for(Publicacion g : publicaciones){
            publicacionesV.add(g);
        }

        for(int i = 0; i < 5 ; i++){
            publicacionesMasValoradas.add(publicacionesV.poll());
        }

        return publicacionesMasValoradas;
    }

    //Métodos para usuarios

    // Método para banear un Estudiante
    public void banearEstudiante(Estudiante estudiante) {
        estudiante.banear();
        usuariosDAO.insertar(List.of(estudiante));
    }

    // Método para desbanear un Estudiante
    public void desbanearEstudiante(Estudiante estudiante) {
        estudiante.desbanear();
        usuariosDAO.insertar(List.of(estudiante));
    }

    // Método para validar si ya existe un nickname
    public boolean validarNicknameDisponible(String nickname){
        boolean nicknameDisponible = true;
        for (Usuario usuario : usuarios) {
            if (usuario.getNickname().equalsIgnoreCase(nickname.trim())) {
                nicknameDisponible = false;
                break;
            }
        }

        return nicknameDisponible;
    }

    // Método para crear estudiante, usa validación de nickname
    public void crearEstudiante(String nombre, String nickname, String contrasenia, String carrera) {
        if (!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname.trim() + "' ya está en uso. Inténtalo con otro."
            );
        }
        Estudiante estudiante = new Estudiante(nombre, nickname.trim(), contrasenia, carrera);
        usuarios.agregar(estudiante);
        usuariosDAO.insertar(List.of(estudiante));
    }

    // Método para crear un moderador, usa validación de nickname
    public void crearModerador(String nombre, String nickname, String contrasenia, String carrera){
        if (!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname.trim() + "' ya está en uso. Inténtalo con otro."
            );
        }
        Moderador moderador = new Moderador(nombre, nickname.trim(), contrasenia, carrera);
        usuarios.agregar(moderador);
        usuariosDAO.insertar(List.of(moderador));
    }

    // Método para seguir un usuario
    public void seguirUsuario(Usuario seguidor, Usuario seguido) {
        if(seguidor == null || seguido == null) {
            throw new IllegalArgumentException("Se ha intentado seguir a un usuario y alguno de los dos usuarios en " +
                    "nulo");
        }

        seguidor.seguirUsuario(seguido);
        usuariosDAO.insertar(List.of(seguidor, seguido));
    }

    // Método para eliminar un estudiante
    public void eliminarEstudiante(Estudiante estudiante){
        if(estudiante == null) {
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

    // Método para actualizar atributos de un estudiante o moderador
    public void actualizarUsuario(Usuario usuario, String nombre, String contrasenia, String carrera){
        if(!nombre.equals(usuario.getNombre())) {
            usuario.setNombre(nombre);
        }
        if(!contrasenia.equals(usuario.getContrasenia())) {
            usuario.setContrasenia(contrasenia);
        }
        if(!carrera.equals(usuario.getCarrera())) {
            usuario.setCarrera(carrera);
        }
        usuariosDAO.insertar(List.of(usuario));
    }

    // Método para actualizar el Nickname de un estudiante
    public void actualizarNicknameEstudiante(Estudiante estudiante, String nickname){
        if(!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
        }

        LinkedList<GrupoEstudio> gruposSolicitante = new LinkedList<>();
        LinkedList<GrupoEstudio> gruposIntegrante = new LinkedList<>();

        for(GrupoEstudio g: gruposEstudio) {
            if(g.getSolicitudes().contains(estudiante)) {
                gruposSolicitante.add(g);
            }

            if(g.getIntegrantes().contains(estudiante)) {
                gruposIntegrante.add(g);
            }
        }

        LinkedList<Publicacion> publicacionesAutor = new LinkedList<>();

        for(Publicacion p: publicaciones) {
            if(p.getAutor().getNickname().equals(estudiante.getNickname())) {
                publicacionesAutor.add(p);
            }
        }

        Estudiante nuevoEstudiante = estudiante.clonar(nickname);
        usuarios.agregar(nuevoEstudiante);

        eliminarEstudiante(estudiante);
        usuariosDAO.eliminar(List.of(estudiante));
        usuariosDAO.insertar(List.of(nuevoEstudiante));

        publicacionesAutor.forEach(p -> p.setAutor(nuevoEstudiante));
        publicacionesDAO.insertar(publicacionesAutor);

        gruposSolicitante.forEach(g -> g.recibirSolicitud(nuevoEstudiante));
        gruposIntegrante.forEach(g -> g.aceptarSolicitud(nuevoEstudiante));

        Collection<GrupoEstudio> grupos = new ArrayList<>(gruposIntegrante.stream().toList());
        grupos.addAll(gruposSolicitante);
        gruposEstudioDAO.insertar(grupos);
    }

    // Método para actualizar el Nickname de un moderador
    public void actualizarNicknameModerador(Moderador moderador, String nickname){
        if(!validarNicknameDisponible(nickname)){
            throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
        }

        LinkedList<Publicacion> publicacionesAutor = new LinkedList<>();

        for(Publicacion p: publicaciones) {
            if(p.getAutor().getNickname().equals(moderador.getNickname())) {
                publicacionesAutor.add(p);
            }
        }

        Moderador moderadorNuevo = moderador.clonar(nickname);
        usuarios.agregar(moderadorNuevo);

        eliminarModerador(moderador);
        usuariosDAO.eliminar(List.of(moderador));
        usuariosDAO.insertar(List.of(moderadorNuevo));

        publicacionesAutor.forEach(p -> p.setAutor(moderadorNuevo));
        publicacionesDAO.insertar(publicacionesAutor);
    }

    // Métodos de grupos de estudio

    // Método para crear un grupo de estudio
    public void crearGrupoEstudio(String nombre) {
        if(nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("No se puede crear un grupo de estudio sin nombre.");
        }

        if(!validarNombreGrupoDisponible(nombre)) {
            throw new IllegalArgumentException("El grupo de estudio '" + nombre + "' ya se encuentra en uso.");
        }

        gruposEstudio.agregar(new GrupoEstudio(nombre));
    }

    public boolean validarNombreGrupoDisponible(String nombre) {
        boolean nombreDisponible = true;
        for(GrupoEstudio g: gruposEstudio) {
            if(g.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                nombreDisponible = false;
                break;
            }
        }

        return nombreDisponible;
    }

    // Método para eliminar un grupo de estudio
    public void eliminarGrupoEstudio(GrupoEstudio grupo){
        if( grupo == null){
            throw new IllegalArgumentException("No se puede eliminar un grupo de estudio nulo.");
        }
        if(!gruposEstudio.contiene(grupo)){
            throw new IllegalArgumentException("No se puede eliminar un grupo de estudio no existente.");
        }

        gruposEstudio.eliminar(grupo);
        gruposEstudioDAO.eliminar(List.of(grupo));
    }

    // Método para actualizar el nombre de un grupo de estudio
    public void actualizarNombreGrupo(GrupoEstudio grupo, String nombre) {
        if(!validarNombreGrupoDisponible(nombre)) {
            throw new IllegalArgumentException("El grupo de estudio '" + nombre + "' ya se encuentra en uso.");
        }

        gruposEstudioDAO.eliminar(List.of(grupo));
        crearGrupoEstudio(nombre);
    }

    // Métodos de publicaciones

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