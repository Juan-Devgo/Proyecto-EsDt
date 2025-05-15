package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Usuario;

import java.util.ArrayList;

public class NodoGrafo<T extends Usuario> {
    private T elemento;
    private final ArrayList<NodoGrafo<T>> adyacentes;

    public NodoGrafo(T elemento) {
        this.elemento = elemento;
        this.adyacentes = new ArrayList<>();
    }

    public boolean existeNodo(NodoGrafo<T> nodo) {
        return adyacentes.contains(nodo);
    }

    public boolean existeCamino(NodoGrafo<T> nodo) {
        if(existeNodo(nodo)) {
            return true;
        }

        for(NodoGrafo<T> adyacente : adyacentes) {
            return existeCamino(nodo);
        }

        return false;
    }

    public void agregarNodo(NodoGrafo<T> nuevoNodo) {
        if(nuevoNodo == null) {
            throw new IllegalArgumentException("Se ha intentado agregar un nodo nulo.");
        }

        adyacentes.add(nuevoNodo);
        nuevoNodo.getAdyacentes().add(this);
    }

    public void eliminarNodo(NodoGrafo<T> nodoAEliminar) {
        if(!existeNodo(nodoAEliminar)) {
            throw new IllegalArgumentException("Se ha intentado eliminar un nodo que no estaba.");
        }

        adyacentes.remove(nodoAEliminar);
        nodoAEliminar.getAdyacentes().remove(this);

    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public ArrayList<NodoGrafo<T>> getAdyacentes() {
        return adyacentes;
    }
}