package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Usuario;

import java.util.Iterator;

public class IteradorGrafoNoDirigido<T extends Usuario> implements Iterator<T> {
    private final Iterator<NodoGrafo<T>> usuarios;

    public IteradorGrafoNoDirigido(Iterator<NodoGrafo<T>> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean hasNext() {
        return usuarios.hasNext();
    }

    @Override
    public T next() {
        return usuarios.next().getElemento();
    }
}