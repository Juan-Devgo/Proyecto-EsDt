package co.edu.uniquindio.proyectoesdt;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Usuario implements InsertableBD, Comparable<Usuario> {
    protected final String nickname;
    protected String nombre, contrasenia, carrera;
    protected ArrayList<String> intereses;
    protected HashSet<Usuario> amigos;
    protected HashSet<Usuario> seguidores;
    protected HashSet<Usuario> seguidos;
    protected Integer numeroConexiones;
    protected boolean activo;

    public Usuario(String nombre, String nickname, String contrasenia, String carrera) {
        if (nombre == null || nombre.isBlank() || nickname == null || nickname.isBlank() || contrasenia == null ||
                contrasenia.isBlank() || carrera == null || carrera.isBlank()) {
            throw new IllegalArgumentException("Al menos uno de los datos suministrados es inválido o nulo al crear " +
                    "un usuario.");
        }

        this.nickname = nickname;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.carrera = carrera;
        this.intereses = new ArrayList<>();
        this.amigos = new HashSet<>();
        this.seguidores = new HashSet<>();
        this.seguidos = new HashSet<>();
        this.numeroConexiones = 0;
        this.activo = true;
    }

    public int compareTo(Usuario u) {
        return u.numeroConexiones.compareTo(numeroConexiones);
    }
    
    public  void mandarMensaje(Chat chat, String contenido){
        if (chat == null){
            throw new IllegalArgumentException("El chat no puede ser nulo.");
        }
        chat.escribirMensaje(this,contenido);
    }



    public int getCantidadAmigos() {
        return amigos.size();
    }

    public void agregarAmigo(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al agregar amigo.");
        }

        amigos.add(us);
    }

    public void eliminarAmigo(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al eliminar amigo.");
        }

        amigos.remove(us);
    }

    public void recibirSeguidor(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al recibir seguidor.");
        }

        seguidores.add(us);
        numeroConexiones++;
        if(seguidos.contains(us)){
            agregarAmigo(us);
        }
    }

    public void seguirUsuario(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al seguir usuario.");
        }

        seguidos.add(us);
        us.recibirSeguidor(this);
        numeroConexiones++;

        if(seguidores.contains(us)){
            agregarAmigo(us);
        }
    }

    public void perderSeguidor(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al perder seguidor.");
        }

        seguidores.remove(us);
        numeroConexiones--;
        if(seguidos.contains(us)){
            eliminarAmigo(us);
        }
    }

    public void noSeguirUsuario(Usuario us) {
        if (us == null) {
            throw new IllegalArgumentException("Valor nulo al no seguir usuario.");
        }

        seguidos.remove(us);
        us.perderSeguidor(this);
        numeroConexiones--;
        if(seguidores.contains(us)){
            eliminarAmigo(us);
        }
    }

    public int getNumeroConexiones() {
        return numeroConexiones;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("valor nulo intentando agregar un nombre");
        }

        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if(contrasenia == null || contrasenia.isBlank()){
            throw new IllegalArgumentException("valor nulo intentando establecer una contraseña");
        }
        this.contrasenia = contrasenia;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public ArrayList<String> getIntereses() {
        return intereses;
    }

    public void agregarInteres(String interes) {
        if(interes == null || interes.isBlank()) {
            throw new IllegalArgumentException("Valor nulo tratando de agregar un interés");
        }

        intereses.add(interes);
    }

    public void eliminarInteres(String interes) {
        if(interes == null || interes.isBlank()) {
            throw new IllegalArgumentException("Valor nulo tratando de eliminar un interés");
        }

        intereses.remove(interes);
    }

    public void setIntereses(ArrayList<String> intereses) {
        this.intereses = intereses;
    }

    public boolean getActivo(){
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public HashSet<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(HashSet<Usuario> amigos) {
        this.amigos = amigos;
    }

    public HashSet<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(HashSet<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public HashSet<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(HashSet<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    public void setNumeroConexiones(Integer numeroConexiones) {
        this.numeroConexiones = numeroConexiones;
    }

    public abstract Usuario clonar(String nickname);
}