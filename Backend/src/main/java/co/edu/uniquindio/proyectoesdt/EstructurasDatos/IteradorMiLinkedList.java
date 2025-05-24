package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import java.util.Iterator;

public class IteradorMiLinkedList<T> implements Iterator<T> {
    private Nodo<T> cabeza;

    public IteradorMiLinkedList(Nodo<T> cabeza) {
        this.cabeza = cabeza;
    }

    @Override
    public boolean hasNext() {
        return cabeza != null;
    }

    @Override
    public T next() {
        T elemento = cabeza.getElemento();
        cabeza = cabeza.getSiguiente();
        return elemento;
    }
}
