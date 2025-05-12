package co.edu.uniquindio.proyectoesdt;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

public class Contenido {
    private HashSet<File> archivos;
    private LinkedList<String> parrafos;

    public Contenido(HashSet<File> archivos, LinkedList<String> parrafos) {
        if(archivos == null || parrafos == null) {
            throw new IllegalArgumentException("Valor nulo al crear el contenido de una publicación.");
        }

    }
}