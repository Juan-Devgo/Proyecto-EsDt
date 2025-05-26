package co.edu.uniquindio.proyectoesdt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class ChatGrupal implements  Iterable<String> {
    private final List<Usuario> participantes;
    private final File mensajes;

    public ChatGrupal(List<Usuario> participantes, File mensajes) {
        this.participantes = participantes;
        this.mensajes = mensajes;
    }

    public boolean esParticipante(Usuario usuario) {
        return participantes.contains(usuario);
    }

    public void escribirMensaje(Usuario emisor, String contenido) {
        if (!esParticipante(emisor)) {
            throw new IllegalArgumentException("El usuario no es parte del grupo.");
        }

        if (contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException("Mensaje vacío.");
        }

        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(mensajes, true))) {
            bfw.write(emisor.getNickname() + "%" + contenido);
            bfw.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo mensaje: " + e.getMessage());
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

}
