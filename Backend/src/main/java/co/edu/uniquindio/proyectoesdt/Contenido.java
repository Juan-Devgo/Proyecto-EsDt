package co.edu.uniquindio.proyectoesdt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Contenido implements InsertableBD {
    private String tituloPublicacion;
    private ArrayList<File> archivos;
    private LinkedList<String> parrafos;

    public Contenido(String tituloPublicacion, ArrayList<File> archivos, LinkedList<String> parrafos) {
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
        this.parrafos = new LinkedList<>();
    }

    public void agregarParrafo(int posicion, String parrafo) {

    }

    public void agregarArchivo(int posicion, File archivo) {

    }

    public void eliminarParrafo(String parrafo) {

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
        this.archivos = archivos;
    }

    public LinkedList<String> getParrafos() {
        return parrafos;
    }

    public void setParrafos(LinkedList<String> parrafos) {
        this.parrafos = parrafos;
    }

    public void setParrafos(File parrafos) {

    }
}