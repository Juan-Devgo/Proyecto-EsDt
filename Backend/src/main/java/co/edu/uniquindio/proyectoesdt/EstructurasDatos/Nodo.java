package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

public class Nodo<T> {
    private T elemento;
    private Nodo<T> siguiente;

    public Nodo(T elemento) {
        this.elemento = elemento;
        this.siguiente = null;
    }

    @Override
    public String toString() {
        return elemento.toString();
    }

    //Getters
    public T getElemento() {
        return elemento;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    //Setters
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }
}