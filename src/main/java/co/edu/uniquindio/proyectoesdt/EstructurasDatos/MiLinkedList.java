package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

public class MiLinkedList<T> {
    private Nodo<T> cabeza;
    private int tamanio;

    public MiLinkedList() {
        cabeza = null;
        tamanio = 0;
    }

    //TDA Mostrar

    @Override
    public String toString() {
        Nodo<T> nodoRecorrido = cabeza; StringBuilder lista = new StringBuilder(); lista.append("[");
        if(cabeza != null) {
            while (nodoRecorrido.getSiguiente() != null) {
                lista.append(nodoRecorrido).append(", ");
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
            lista.append(nodoRecorrido);
        } else {
            lista.append(" ");
        }
        lista.append("]");
        return lista.toString();
    }

    //TAD Verificar es vacía

    public boolean esVacia() {
        return cabeza == null;
    }

    //TDA Insersión

    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (!esVacia()) {
            Nodo<T> nodoRecorrido = cabeza;
            while (nodoRecorrido.getSiguiente() != null) {
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
            nodoRecorrido.setSiguiente(nuevoNodo);
            tamanio++;
            return;
        }

        cabeza = nuevoNodo;
        tamanio++;
    }

    public void agregarAlInicio(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
        tamanio++;
    }

    public void agregarEnPosicion(T elemento, int posicion) {
        if(posicion <= tamanio && posicion >= 0) {

            if(posicion == 0){
                agregarAlInicio(elemento);
                return;
            }

            if(posicion == tamanio) {
                agregar(elemento);
                return;
            }

            Nodo<T> nuevoNodo = new Nodo<>(elemento);

            Nodo<T> nodoRecorrido = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
            nuevoNodo.setSiguiente(nodoRecorrido.getSiguiente());
            nodoRecorrido.setSiguiente(nuevoNodo);
            tamanio++;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    //TDA Obtener

    public T obtener(int posicion) {

        T elemento = null;

        if(!esVacia()) {
            if (posicion < tamanio && posicion >= 0) {

                Nodo<T> nodoRecorrido = cabeza;
                for (int i = 0; i < posicion; i++) {
                    nodoRecorrido = nodoRecorrido.getSiguiente();
                }
                elemento = nodoRecorrido.getElemento();

            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        return elemento;
    }

    public T obtenerPrimero() {

        T elemento = null;

        if(!esVacia()){
            elemento = cabeza.getElemento();
        }

        return elemento;
    }

    public T obtenerUltimo() {

        T elemento = null;

        if(!esVacia()) {

            Nodo<T> nodoRecorrido = cabeza;
            while (nodoRecorrido.getSiguiente() != null) {
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
            elemento = nodoRecorrido.getElemento();
        }

        return elemento;
    }

    //TDA Eliminación

    public void eliminar(int posicion) {

        if(!esVacia()) {
            if (posicion < tamanio && posicion >= 0) {

                if (posicion == 0) {
                    eliminarPrimero();
                    return;
                }

                if (posicion == tamanio - 1) {
                    eliminarUltimo();
                    return;
                }

                Nodo<T> nodoRecorrido = cabeza;
                for (int i = 0; i < posicion - 1; i++) {
                    nodoRecorrido = nodoRecorrido.getSiguiente();
                }
                nodoRecorrido.setSiguiente(nodoRecorrido.getSiguiente().getSiguiente());
                tamanio--;

            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    public void eliminar(T elemento) {
        if(!esVacia()) {

            if(cabeza.getElemento().equals(elemento)) {
                eliminarPrimero();
                return;
            }

            Nodo<T> nodoRecorrido = cabeza;
            while (nodoRecorrido.getSiguiente() != null) {
                if(elemento.equals(nodoRecorrido.getSiguiente().getElemento())) {
                    nodoRecorrido.setSiguiente(nodoRecorrido.getSiguiente().getSiguiente());
                    tamanio--;
                    break;
                }
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
        }
    }

    public void eliminarPrimero() {
        if(!esVacia()) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
        }
    }

    public void eliminarUltimo() {
        if(!esVacia()) {

            if(cabeza.getSiguiente() == null) {
                eliminarPrimero();
                return;
            }

            Nodo<T> nodoRecorrido = cabeza;
            while (nodoRecorrido.getSiguiente().getSiguiente() != null) {
                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
            nodoRecorrido.setSiguiente(null);
            tamanio--;
        }
    }

    //TDA Buscar

    public int buscar(T elemento) {

        int pos = -1;
        if(!esVacia()){

            Nodo<T> nodoRecorrido = cabeza;
            for (int i = 0; i < tamanio; i++) {

                if(nodoRecorrido.getElemento().equals(elemento)) {
                    pos = i;
                    break;
                }

                nodoRecorrido = nodoRecorrido.getSiguiente();
            }
        }

        return pos;
    }

    public boolean contiene(T elemento) {

        boolean contiene = false;
        if(!esVacia()) {

            if (cabeza.getElemento().equals(elemento)) {
                contiene = true;
            } else {

                Nodo<T> nodoRecorrido = cabeza;
                while (nodoRecorrido.getSiguiente() != null) {
                    if (nodoRecorrido.getElemento().equals(elemento)) {
                        contiene = true;
                        break;
                    }
                    nodoRecorrido = nodoRecorrido.getSiguiente();
                }
            }
        }

        return contiene;
    }

    public int getTamanio() {
        return tamanio;
    }
}