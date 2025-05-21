package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import java.util.Iterator;

public class IteradorColaPrioridad<T> implements Iterator<T>{
    private Nodo<T> actual;

    public IteradorColaPrioridad(Nodo<T> cabeza) {
        this.actual = cabeza;
    }

    @Override
    public boolean hasNext() {
        return actual != null;
    }

    @Override
    public T next() {
        T elemento = actual.getElemento();
        actual = actual.getSiguiente();
        return elemento;
    }
}