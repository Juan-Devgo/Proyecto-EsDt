package co.edu.uniquindio.proyectoesdt;

import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.File;

public class IteradorChat implements Iterator<String>{
    private final BufferedReader reader;
    private String nextLine;

    public IteradorChat(File archivoMensajes) throws IOException {
        this.reader = new BufferedReader(new FileReader(archivoMensajes));
        this.nextLine = reader.readLine();
    }
    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String next() {
        String actual = nextLine;
        try {
            nextLine = reader.readLine();
            if (nextLine == null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return actual;
    }
}