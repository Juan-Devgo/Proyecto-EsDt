package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Publicacion;

import java.util.Iterator;
import java.util.Stack;

public class IteradorArbolBinarioABB<T extends Publicacion> implements Iterator<T> {
    private final Stack<NodoArbol<T>> pila = new Stack<>();

    public IteradorArbolBinarioABB(NodoArbol<T> raiz) {
        pushIzquierda(raiz);
    }

    @Override
    public boolean hasNext() {
        return !pila.isEmpty();
    }

    @Override
    public T next() {
        //Recorrido por Inorden
        NodoArbol<T> actual = pila.pop();
        T elemento = actual.getElemento();
        if(actual.getDerecha() != null) {
            pushIzquierda(actual.getDerecha());
        }

        return elemento;
    }

    public void pushIzquierda(NodoArbol<T> nodo) {
        if(nodo == null) {
            return;
        }

        pila.push(nodo);
        nodo = nodo.getIzquierda();
        pushIzquierda(nodo);
    }
}