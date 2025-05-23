package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import co.edu.uniquindio.proyectoesdt.Patrones.GruposEstudioDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.PublicacionesDAO;
import co.edu.uniquindio.proyectoesdt.Patrones.UsuariosDAO;
import co.edu.uniquindio.proyectoesdt.util.Logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        }

        // Singleton
        public static Plataforma getInstancia() {
            if (instancia == null) {
                instancia = new Plataforma();
            }
            return instancia;
        }

        // Método para generar grafo no dirigido: COMPLETAR
        public int[][] generarGrafo() {
            return usuarios.obtenerMatrizAdyacencia();
        }

        // Método para generar Grupos de Estudio: COMPLETAR
        public void generarGruposEstudio() {}

        // Método para banear un Estudiante
        public void banearEstudiante(Estudiante estudiante, Moderador moderador) {
            estudiante.banear();
            Logging.logInfo("El moderador " + moderador.nickname + " ha baneado al usuario " + estudiante.nickname +
                    ".",this);
            usuariosDAO.insertar(List.of(estudiante));
        }

        // Método para desbanear un Estudiante
        public void desbanearEstudiante(Estudiante estudiante, Moderador moderador) {
            estudiante.desbanear();
            Logging.logInfo("El moderador " + moderador.nickname + " ha desbaneado al usuario " + estudiante.nickname +
                    ".",this);
            usuariosDAO.insertar(List.of(estudiante));
        }

        // Método para validar si ya existe un nickname
        public boolean validarNicknameDisponible(String nickname){
            for (NodoGrafo<? extends Usuario> nodo : usuarios.getUsuarios()) {
                Usuario usuario = nodo.getElemento();
                if (usuario.getNickname().equalsIgnoreCase(nickname)) {
                    return false;
                }
            }
            return true;
        }

        // Método para crear estudiante, usa validacion de nickname
        public Estudiante crearEstudiante(String nombre, String nickname, String contrasenia, String carrera) {
            if (!validarNicknameDisponible(nickname)) {
                throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
            }
            Estudiante estudiante = new Estudiante(nombre, nickname, contrasenia, carrera);
            usuarios.agregar(estudiante);
            return estudiante;
        }

        // Método para crear una publicacion, usa validacion de nickname
        public Moderador crearModerador(String nombre, String nickname, String contrasenia, String carrera){
            if (!validarNicknameDisponible(nickname)) {
                throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
            }
            Moderador moderador = new Moderador(nombre, nickname, contrasenia, carrera);
            usuarios.agregar(moderador);
            return moderador;
        }

        // Método para eliminar un estudiante
        public void eliminarEstudiante(Estudiante estudiante){
            if(!usuarios.existe(estudiante)) {
                throw new IllegalArgumentException("Se ha intentado eliminar un estudiante nulo.");
            }
            usuarios.eliminar(estudiante);
            //usuariosDAO.
        }

        // Método para eliminar un moderador
        public void eliminarModerador(Moderador moderador){
            if(!usuarios.existe(moderador)) {
                throw new IllegalArgumentException("Se ha intentado eliminar un moderador nulo.");
            }
            usuarios.eliminar(moderador);
            //usuariosDAO.
        }

        // Método para actualizar el nombre de un estudiante o moderador
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
                throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
            }
            if(!nickname.equals(estudiante.getNickname())){
                Estudiante estudianteNuevo = new Estudiante(estudiante.nombre, nickname, estudiante.contrasenia, estudiante.carrera);
                usuarios.agregar(estudianteNuevo);
                eliminarEstudiante(estudiante);
                //usuariosDAO.eliminar
                usuariosDAO.insertar(List.of(estudianteNuevo));
            }
        }

        // Metodo para actualizar el nickname de un moderador
        public void actualizarNicknameModerador(Moderador moderador, String nickname){
            if(!validarNicknameDisponible(nickname)){
                throw new IllegalArgumentException("El nickname '" + nickname + "' ya está en uso. Inténtalo con otro.");
            }

            if(!nickname.equals(moderador.getNickname())){
                Moderador moderadorNuevo = new Moderador(moderador.nombre, nickname, moderador.contrasenia, moderador.carrera);
                usuarios.agregar(moderadorNuevo);
                eliminarModerador(moderador);
                //usuariosDAO.eliminar
                usuariosDAO.insertar(List.of(moderadorNuevo));
            }
        }

        // Método para eliminar una publicación
        public void eliminarPublicacion(Publicacion publicacion){
            if(publicacion == null){
                throw  new IllegalArgumentException("No se puede eliminar una publicacion nula.");
            }
            if (!publicaciones.existe(publicacion)){
                throw  new RuntimeException("No se puede eliminar una publicacio no existente.");
            }
            publicaciones.eliminar(publicacion);
            //
        }

        // Método para eliminar un grupo de estudio
        public void eliminarGrupoEstudio(GrupoEstudio grupo){
            if( grupo == null){
                throw new IllegalArgumentException("No se puede eliminar un grupo nulo. ");
            }
            if(!gruposEstudio.contiene(grupo)){
                throw new IllegalArgumentException("No se puede eliminar un grupo que no existe.");
            }
            gruposEstudio.eliminar(grupo);
            //
        }

        // Método para buscar un estudiante
        public Estudiante buscarEstudiante(String nickname) {
            Estudiante est = null;

            if(nickname == null || nickname.isBlank()){
                throw new IllegalArgumentException(" No se puede encontrar un estudiante con nickname nulo.");
            }

            Optional<Estudiante> optEst = usuarios.getUsuarios().stream()
                    .filter(nodoUsuario -> nodoUsuario.getElemento() instanceof Estudiante)
                    .map(nodoEstudiante -> (Estudiante) nodoEstudiante.getElemento())
                    .filter(estudiante -> estudiante.nickname.equals(nickname)).findFirst();

            if(optEst.isPresent()) {
                est = optEst.get();
            }

            return est;
        }

        // Método para buscar un moderador
        public Moderador buscarModerador(String nickname) {
            Moderador mod = null;

            if(nickname == null || nickname.isBlank()){
                throw new IllegalArgumentException(" No se puede encontrar un moderador con nickname nulo.");
            }

            Optional<Moderador> optMod = usuarios.getUsuarios().stream()
                    .filter(nodoUsuario -> nodoUsuario.getElemento() instanceof Moderador)
                    .map(nodoModerador -> (Moderador) nodoModerador.getElemento())
                    .filter(moderador -> moderador.nickname.equals(nickname)).findFirst();

            if(optMod.isPresent()) {
                mod = optMod.get();
            }

            return mod;
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