package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

public class NodoDoble<T> {
    private T elemento;
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;

    public NodoDoble(T elemento) {
        this.elemento = elemento;
        siguiente = null;
        anterior = null;
    }

    @Override
    public String toString() {
        return elemento.toString();
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }
}
