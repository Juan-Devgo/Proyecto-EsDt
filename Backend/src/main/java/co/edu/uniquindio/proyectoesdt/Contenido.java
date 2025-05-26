package co.edu.uniquindio.proyectoesdt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Contenido implements InsertableBD {
    private final String tituloPublicacion;
    private ArrayList<File> archivos;
    private ArrayList<String> parrafos;

    public Contenido(String tituloPublicacion, ArrayList<File> archivos, ArrayList<String> parrafos) {
        if(tituloPublicacion == null || tituloPublicacion.isBlank() || archivos == null || parrafos == null) {
            throw new IllegalArgumentException("Valor nulo al crear el contenido de una publicación.");
        }

        this.tituloPublicacion = tituloPublicacion;
        this.archivos = archivos;
        this.parrafos = parrafos;
    }

    public Contenido(String tituloPublicacion) {
        if(tituloPublicacion == null || tituloPublicacion.isBlank()) {
            throw new IllegalArgumentException("Valor nulo al crear el contenido de una publicación.");
        }

        this.tituloPublicacion = tituloPublicacion;
        this.archivos = new ArrayList<>();
        this.parrafos = new ArrayList<>();
    }

    public void agregarParrafo(int posicion, String parrafo) {
        if (parrafo == null) {
            throw new IllegalArgumentException("Párrafo nulo.");
        }
        while (parrafos.size() <= posicion) {
            parrafos.add("");
        }
        parrafos.set(posicion, parrafo);
    }

    public void agregarArchivo(int posicion, File archivo) {
        if (archivo == null) {
            throw new IllegalArgumentException("Archivo nulo.");
        }

        while (archivos.size() <= posicion) {
            archivos.add(null);
        }

        archivos.set(posicion, archivo);
    }

    public void eliminarParrafo(String parrafo) {
        if(parrafo == null || parrafo.isBlank()) {
            throw new IllegalArgumentException("Valor nulo al eliminar un párrafo de una publicación.");
        }

        parrafos.remove(parrafo);
    }

    public File getParrafosFile() throws IOException {
        File parrafosFile = new File("files", tituloPublicacion.trim() + "_parrafos.txt");

        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(parrafosFile))) {
            for (String parrafo : parrafos) {
                bfw.write(parrafo);
                bfw.newLine();
            }
        }

        return parrafosFile;
    }

    public ArrayList<File> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<File> archivos) {
        if(archivos == null) {
            throw new IllegalArgumentException("Valor nulo al cambiar los archivos de una publicación.");
        }

        this.archivos = archivos;
    }

    public ArrayList<String> getParrafos() {
        return parrafos;
    }

    public void setParrafos(File archivoParrafos) {
        if (archivoParrafos == null) {
            throw new IllegalArgumentException("Archivo nulo al establecer los párrafos.");
        }

        ArrayList<String> nuevosParrafos = new ArrayList<>();

        try (Scanner scanner = new Scanner(archivoParrafos)) {
            while (scanner.hasNextLine()) {
                nuevosParrafos.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No se ha encontrado el archivo para set: " + archivoParrafos.getName());
        }

        this.parrafos = nuevosParrafos;
    }

    public String getTituloPublicacion() {
        return tituloPublicacion;
    }
}