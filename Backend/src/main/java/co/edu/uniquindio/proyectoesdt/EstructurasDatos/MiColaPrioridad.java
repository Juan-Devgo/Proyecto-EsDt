package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.SolicitudAyuda;

import java.util.Iterator;

public class MiColaPrioridad<T extends SolicitudAyuda> extends MiCola<T> implements Iterable<T>  {
    @Override
    public void encolarCola(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);

        if (cabeza == null || elemento.compareTo(cabeza.getElemento()) < 0) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null && elemento.compareTo(actual.getSiguiente().getElemento()) >= 0) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public Nodo<T> buscar(T elemento) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public T obtener() {
        return (cabeza != null) ? cabeza.getElemento() : null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.getElemento()).append(", ");
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorColaPrioridad<>(cabeza);
    }

}


