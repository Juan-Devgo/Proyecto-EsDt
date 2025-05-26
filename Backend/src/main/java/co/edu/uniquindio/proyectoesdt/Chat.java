package co.edu.uniquindio.proyectoesdt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Chat implements Iterable<String> {
    private final Usuario usuario1;
    private final Usuario usuario2;
    private final File mensajes;

    public Chat(Usuario usuario1, Usuario usuario2, File mensajes ){
        if (usuario1 == null || usuario2 == null) {
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos.");
        }
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = mensajes;
    }

    public boolean participante(Usuario usuario) {
        return usuario.equals(usuario1) || usuario.equals(usuario2);
    }

    public void escribirMensaje(Usuario emisor, String contenido){
        if (!participante(emisor)) {
            throw new IllegalArgumentException("El usuario no pertenece a este chat.");
        }
        if (contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }

        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(this.mensajes, true))){
            bfw.write(emisor.getNickname() + "%" + contenido);
            bfw.newLine();
        } catch (IOException e){
            System.out.println("Error al escribir el mensaje: " + e.getMessage());
        }
    }

    @Override
    public Iterator<String> iterator() {
        try {
            return new IteradorChat(mensajes);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de mensajes.", e);
        }
    }


    public Usuario getUsuario2(){
        return usuario2;
    }

    public File getMensajes(){
        return mensajes;
    }
}