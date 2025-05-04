package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

public class MiCola<T> {
    protected int tamanio;
    protected Nodo<T> cabeza;
    protected Nodo<T> cola;

    public MiCola() {
        tamanio = 0;
        cabeza = null;
        cola = null;
    }

    //TAD Mostrar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(!esVacia()){
            Nodo<T> actual = cabeza;
            while(!actual.equals(cola)){
                sb.append(actual).append(", ");
                actual = actual.getSiguiente();
            }
            sb.append(actual);
        } else {
            sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    //TAD Verificar es Vacía
    public boolean esVacia() {
        return cabeza == null;
    }

    //TAD Agregar al final
    public void encolarCola(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (esVacia()) {
            cabeza = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
        }
        cola = nuevoNodo;
        tamanio++;
    }

    //TAD obtener primer elemento (sin eliminar)
    public T obtenerCabeza() {
        T elemento = null;
        if(!esVacia()) {
            elemento = cabeza.getElemento();
        }
        return elemento;
    }

    //TAD obtener primer elemento (eliminando el elemento)
    public T sacarCabeza() {
        T elemento = null;
        if(!esVacia()) {
            elemento = cabeza.getElemento();
            desencolarCabeza();
        }
        return elemento;
    }

    //TAD Quitar el primer elemento
    public void desencolarCabeza() {
        if(!esVacia()) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
        }
    }

}
