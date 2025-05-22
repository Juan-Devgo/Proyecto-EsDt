package co.edu.uniquindio.proyectoesdt;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Usuario implements InsertableBD, Comparable<Usuario> {
    protected String nombre, nickname, contrasenia, carrera;
    protected final ArrayList<String> intereses;
    protected final HashSet<Usuario> amigos;
    protected final HashSet<Usuario> seguidores;
    protected final HashSet<Usuario> seguidos;
    protected Integer numeroConexiones = 0;
    protected boolean activo = true;

    public Usuario(String nombre, String nickname, String contrasenia, String carrera) {
        if (nombre == null || nombre.isBlank() || nickname == null || nickname.isBlank() || contrasenia == null ||
                contrasenia.isBlank() || carrera == null || carrera.isBlank()) {
            throw new IllegalArgumentException("Al menos uno de los datos suministrados es inválido o nulo al crear " +
                    "un usuario.");
        }

        this.intereses = new ArrayList<>();
        this.amigos = new HashSet<>();
        this.seguidores = new HashSet<>();
        this.seguidos = new HashSet<>();
    }

    public int compareTo(Usuario u) {
        return u.numeroConexiones.compareTo(numeroConexiones);
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

    /*public void setNickname(String nickname) {
        this.nickname = nickname;
    }*/

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

    public boolean getActivo(){
        return activo;
    }

    public HashSet<Usuario> getAmigos() {
        return amigos;
    }

    public HashSet<Usuario> getSeguidores() {
        return seguidores;
    }

    public HashSet<Usuario> getSeguidos() {
        return seguidos;
    }
}