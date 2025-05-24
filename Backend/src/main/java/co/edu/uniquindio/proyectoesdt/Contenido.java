package co.edu.uniquindio.proyectoesdt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        while(parrafos.size() <= posicion) {
            parrafos.add("");
        }

        parrafos.add(posicion, parrafo);

    }

    public void agregarArchivo(int posicion, File archivo) {
        while(archivos.size() <= posicion) {
            archivos.add(null);
        }

        archivos.add(posicion, archivo);

    }

    public void eliminarParrafo(String parrafo) {
        if(parrafo == null || parrafo.isBlank()) {
            throw new IllegalArgumentException("Valor nulo al eliminar un párrafo de una publicación.");
        }

        parrafos.remove(parrafo);
    }

    public File getParrafosFile() throws IOException {
        File parrafosFile = new File("files\\" + tituloPublicacion.trim() + "_parrafos");
        BufferedWriter bfw = new BufferedWriter(new FileWriter(parrafosFile));

        for(String parrafo: parrafos) {
            bfw.write(parrafo);
            bfw.newLine();
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

    public void setParrafos(ArrayList<String> parrafos) {
        if(parrafos == null) {
            throw new IllegalArgumentException("Valor nulo al cambiar los párrafos de una publicación.");
        }

        this.parrafos = parrafos;
    }

    public void setParrafos(File parrafos) {
        ArrayList<String> nuevosParrafos = new ArrayList<>();
        //try {
            Scanner scanner = new Scanner("files\\" + parrafos.getName());
            while(scanner.hasNext()) {
                nuevosParrafos.add(scanner.next());
            }
            this.parrafos = nuevosParrafos;
        /*} catch (IOException e) {
            throw new RuntimeException("No se ha encontrado el archivo para set: " + parrafos.getName());
        }*/
    }
}