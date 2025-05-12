package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Publicacion;

public class NodoArbol<T extends Publicacion> {

    private T elemento;
    private NodoArbol<T> izquierda;
    private NodoArbol<T> derecha;

    public NodoArbol(T elemento) {
        this.elemento = elemento;
        this.izquierda = null;
        this.derecha = null;
    }

    public boolean esHoja() {
        return izquierda == null && derecha == null;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NodoArbol<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoArbol<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoArbol<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoArbol<T> derecha) {
        this.derecha = derecha;
    }
}