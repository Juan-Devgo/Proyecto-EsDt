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

            if(p instanceof SolicitudAyuda s) {
                solicitudesAyuda.encolarCola(s);
            }
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
        return usuarios.encontrarCaminoCorto(usuario1,usuario2);
    }

    public LinkedList<GrupoEstudio> obtenerGruposMasValorados(){
        PriorityQueue<GrupoEstudio> grupos = new PriorityQueue<>();
        LinkedList<GrupoEstudio> grupoEstudioMasValorados = new LinkedList<>();
        for(GrupoEstudio g: gruposEstudio) {
            grupos.add(g);
        }

        for(int i = 0; i < 5 && !grupos.isEmpty(); i++) {
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

        for(int i = 0; i < 5 && !usuariosC.isEmpty(); i++) {
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

        for(int i = 0; i < 5 && !publicacionesV.isEmpty(); i++){
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

    // Método para validar si ya existe un nickname
    public boolean validarNicknameDisponible(String nickname){
        boolean nicknameDisponible = true;
        for (Usuario usuario : usuarios) {
            if (usuario.getNickname().equals(nickname.trim())) {
                nicknameDisponible = false;
                break;
            }
        }

        return nicknameDisponible;
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

        LinkedList<GrupoEstudio> gruposAux = new LinkedList<>();
        for(GrupoEstudio g: gruposEstudio) {
            if(g.getSolicitudes().contains(estudiante)) {
                g.rechazarSolicitud(estudiante);
                gruposAux.add(g);
            }

            if(g.getIntegrantes().contains(estudiante)) {
                g.eliminarIntegrante(estudiante);
                gruposAux.add(g);
            }
        }

        LinkedList<Publicacion> publicacionesAux = new LinkedList<>();
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

        gruposEstudioDAO.insertar(gruposAux);
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

        LinkedList<Publicacion> publicacionesAux = new LinkedList<>();
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

    // Método para buscar varios usuarios
    public Collection<Usuario> buscarUsuariosNicknameONombre(String nombre) {
        if(nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("No se puede buscar por nombres en blanco");
        }

        Collection<Usuario> usauriosEncontrados = new ArrayList<>();
        for(Usuario u: usuarios) {
            if(u.getNickname().toLowerCase().trim().contains(nombre.toLowerCase().trim()) ||
                    u.getNombre().toLowerCase().trim().contains(nombre.toLowerCase().trim())) {
                usauriosEncontrados.add(u);
            }
        }

        return usauriosEncontrados;
    }

    // Método para actualizar atributos de un estudiante o moderador
    public void actualizarUsuario(Usuario usuario, String nombre, String contrasenia, String carrera){
        if(usuario == null) {
            throw new IllegalArgumentException("No se pueden cambiar los atributos de un usuario nulo.");
        }

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
        if(estudiante == null) {
            throw new IllegalArgumentException("No se puede cambiar el nickname a un estudiante nulo.");
        }

        if(!validarNicknameDisponible(nickname)) {
            throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
        }

        LinkedList<GrupoEstudio> gruposSolicitante = new LinkedList<>();
        LinkedList<GrupoEstudio> gruposIntegrante = new LinkedList<>(estudiante.getGruposEstudio());

        for(GrupoEstudio g: gruposEstudio) {
            if(g.getSolicitudes().contains(estudiante)) {
                gruposSolicitante.add(g);
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
        if(moderador == null) {
            throw new IllegalArgumentException("No se puede cambiar el nickname a un moderador nulo.");
        }

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
        usuariosDAO.insertar(List.of(moderadorNuevo));

        publicacionesAutor.forEach(p -> p.setAutor(moderadorNuevo));
        publicacionesDAO.insertar(publicacionesAutor);
    }

    // Métodos de grupos de estudio

    // Método para crear un grupo de estudio
    public void crearGrupoEstudio(String nombre) {
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

    // Método para buscar un grupo de estudio
    public GrupoEstudio buscarGrupoEstudio(String nombre) {
        GrupoEstudio grupoEncontrado = null;

        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException(" No se puede encontrar un grupo con nombre nulo.");
        }

        for(GrupoEstudio g: gruposEstudio) {
            if(g.getNombre().equals(nombre)){
                grupoEncontrado = g;
                break;
            }
        }

        return grupoEncontrado;
    }

    // Método para buscar varios grupos de estudio por nombre o tema
    public Collection<GrupoEstudio> buscarGruposEstudio(String busqueda) {
        if(busqueda == null || busqueda.isBlank()) {
            throw new IllegalArgumentException("No se pueden buscar grupos de estudio.");
        }

        Collection<GrupoEstudio> gruposEncontrados = new HashSet<>();
        for(GrupoEstudio g: gruposEstudio) {
            if(g.getNombre().toLowerCase().trim().contains(busqueda.toLowerCase().trim())) {
                gruposEncontrados.add(g);
            }

            for(String tema: g.getTemas()) {
                if(tema.toLowerCase().trim().contains(busqueda.toLowerCase().trim())) {
                    gruposEncontrados.add(g);
                }
            }
        }

        return gruposEncontrados;
    }

    // Método para actualizar los temas de un grupo de estudio
    public void actualizarTemasGrupo(GrupoEstudio grupo, ArrayList<String> temas) {
        if(temas == null) {
            throw  new IllegalArgumentException("No se pueden actualizar los temas ya que son nulos.");
        }

        grupo.setTemas(temas);
        gruposEstudioDAO.actualizartemas(grupo);
    }

    // Método para actualizar el nombre de un grupo de estudio
    public void actualizarNombreGrupo(GrupoEstudio grupo, String nombre) {
        if(!validarNombreGrupoDisponible(nombre)) {
            throw new IllegalArgumentException("El grupo de estudio '" + nombre + "' ya se encuentra en uso.");
        }

        GrupoEstudio nuevoGrupo = grupo.clonar(nombre);
        gruposEstudio.agregar(nuevoGrupo);

        eliminarGrupoEstudio(grupo);
        gruposEstudioDAO.insertar(List.of(nuevoGrupo));
    }

    // Método para agregar integrante a grupo de estudio
    public void agregarIntegranteGrupo(GrupoEstudio grupo, Estudiante estudiante) {
        if(grupo == null || estudiante == null ) {
            throw new IllegalArgumentException("No se puede agregar integrante a grupo ya que hay al menos un dato nulo"
            );
        }

        grupo.aceptarSolicitud(estudiante);
        gruposEstudioDAO.actualizarIntegrantes(grupo);
        gruposEstudioDAO.actualizarSolicitudes(grupo);
    }

    // Método para agregar una solicitud a grupo de estudio
    public void agregarSolicitudGrupo(GrupoEstudio grupo, Estudiante estudiante) {
        if(grupo == null || estudiante == null ) {
            throw new IllegalArgumentException("No se puede agregar una solicitud a grupo ya que hay al menos un " +
                    "dato nulo");
        }

        grupo.recibirSolicitud(estudiante);
        gruposEstudioDAO.actualizarSolicitudes(grupo);
    }

    // Método para eliminar un integrante de un grupo de estudio
    public void eliminarIntegranteGrupo(GrupoEstudio grupo, Estudiante estudiante) {
        if(grupo == null || estudiante == null ) {
            throw new IllegalArgumentException("No se puede eliminar un integrante del grupo ya que hay al menos un " +
                    "dato nulo");
        }

        grupo.eliminarIntegrante(estudiante);
        gruposEstudioDAO.actualizarIntegrantes(grupo);
    }

    // Método para eliminar una solicitud a grupo de estudio
    public void eliminarSolicitudGrupo(GrupoEstudio grupo, Estudiante estudiante) {
        if(grupo == null || estudiante == null ) {
            throw new IllegalArgumentException("No se puede eliminar una solicitud a grupo ya que hay al menos un " +
                    "dato nulo");
        }

        grupo.rechazarSolicitud(estudiante);
        gruposEstudioDAO.actualizarSolicitudes(grupo);
    }

    // Métodos de publicaciones

    // Método para crear una publicación
    public void crearPublicacion(String titulo, String tema, Usuario autor, TipoPublicacion tipo, Contenido contenido) {
        if(!validarTituloPublicacionDisponible(titulo)) {
            throw new IllegalArgumentException("El título '" + titulo + "' ya se encuentra en uso.");
        }

        Publicacion publicacion = new Publicacion(titulo, tema, autor, tipo, contenido);

        publicaciones.agregar(publicacion);
        publicacionesDAO.insertar(List.of(publicacion));
    }

    // Método para crear una solicitud de ayuda
    public void crearSolicitudAyuda(String titulo, String tema, Usuario autor, Contenido contenido, Prioridad prioridad)
    {
        if (!validarTituloPublicacionDisponible(titulo)) {
            throw new IllegalArgumentException("El título '" + titulo + "' ya se encuentra en uso.");
        }

        SolicitudAyuda solicitud = new SolicitudAyuda(titulo, tema, autor, contenido, true, prioridad);

        publicaciones.agregar(solicitud);
        solicitudesAyuda.encolarCola(solicitud);
        publicacionesDAO.insertar(List.of(solicitud));
    }

    private boolean validarTituloPublicacionDisponible(String titulo) {
        boolean tituloDisponible = true;
        for(Publicacion p: publicaciones) {
            if(p.getTitulo().trim().equalsIgnoreCase(titulo.trim())) {
                tituloDisponible = false;
                break;
            }
        }

        return tituloDisponible;
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

    // Método para buscar una publicación
    public Publicacion buscarPublicacion(String titulo) {
        Publicacion publicacion = null;

        for(Publicacion p: publicaciones) {
            if(p.getTitulo().trim().equalsIgnoreCase(titulo.trim())) {
                publicacion = p;
                break;
            }
        }

        return publicacion;
    }

    // Método para buscar varias publicaciones por tema, autor y tipo
    public Collection<Publicacion> buscarPublicacionesTema(String busqueda) {
        if(busqueda == null || busqueda.isBlank()) {
            throw new IllegalArgumentException("No se puede buscar publicaciones con un tema en blanco");
        }

        Collection<Publicacion> publicacionesEncontradas = new LinkedList<>();
        for(Publicacion p: publicaciones) {
            if(p.getTema().toLowerCase().contains(busqueda.trim().toLowerCase()) ||
                    p.getAutor().getNickname().toLowerCase().trim().contains(busqueda.toLowerCase().trim()) ||
                    p.getAutor().getNombre().toLowerCase().trim().contains(busqueda.toLowerCase().trim()) ||
                    p.getTipoPublicacion().toString().toLowerCase().trim().contains(busqueda.toLowerCase().trim())
            ) {
                publicacionesEncontradas.add(p);
            }
        }
        return publicacionesEncontradas;
    }

    // Método para actualizar atributos de una publicación
    public void actualizarPublicacion(Publicacion p, String tema, Usuario autor, TipoPublicacion tipo,
                                      Contenido contenido) {
        if(p == null) {
            throw new IllegalArgumentException("No se puede actualizar una publicación nula.");
        }

        if(!tema.equals(p.getTema())) {
            p.setTema(tema);
        }

        if(!autor.equals(p.getAutor()) && usuarios.existe(autor)) {
            p.setAutor(autor);
        }

        if(!tipo.equals(p.getTipoPublicacion())) {
            p.setTipoPublicacion(tipo);
        }

        if(!contenido.equals(p.getContenido())) {
            p.setContenido(contenido);
        }

        publicacionesDAO.insertar(List.of(p));
    }

    // Método para actualizar atributos a una solicitud de ayuda
    public void actualizarSolicitudAyuda(SolicitudAyuda s, Prioridad prioridad, boolean activa) {
        if(s == null) {
            throw new IllegalArgumentException("No se puede actualizar una solicitud de ayuda nula.");
        }

        if(!prioridad.equals(s.getPrioridad())) {
            s.setPrioridad(prioridad);
        }

        if(activa != s.isActiva()) {
            s.setActiva(activa);
        }

        publicacionesDAO.insertar(List.of(s));
    }

    // Método para actualizar el título a una publicación
    public void actualizarTituloPublicacion(Publicacion publicacion, String titulo) {
        if(!validarTituloPublicacionDisponible(titulo)) {
            throw new IllegalArgumentException("El título '" + titulo + "' ya se encuentra en uso.");
        }

        Publicacion nuevaPublicacion = publicacion.clonar(titulo);
        publicaciones.agregar(nuevaPublicacion);

        eliminarPublicacion(publicacion);
        publicacionesDAO.insertar(List.of(nuevaPublicacion));
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