package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Publicacion;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ArbolBinarioABB<T extends Publicacion> implements Iterable<T> {
    private int peso;
    private NodoArbol<T> raiz;

    public ArbolBinarioABB() {
        this.peso = 0;
        this.raiz = null;
    }

    private ArbolBinarioABB(NodoArbol<T> raiz) {
        this.peso = 0;
        this.peso = obtenerPesoSubarbol(raiz, peso);
        this.raiz = raiz;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorArbolBinarioABB<>(raiz);
    }

    //TDA Está vacío
    public boolean esVacio() {
        return raiz == null;
    }

    //TDA Insersión
    public void agregar(T elemento) {
        NodoArbol<T> nuevoNodo = new NodoArbol<>(elemento);
        if (esVacio()) {
            raiz = nuevoNodo;
            peso++;
            return;
        }

        if (!existe(elemento)) {
            agregarNodoRecursivo(nuevoNodo, raiz);
        }
    }

    //TDA Eliminación
    public void eliminar(T elemento) {
        if (existe(elemento)) {
            if (raiz.getElemento().equals(elemento)) {
                eliminarPrimero();
                return;
            }
            eliminarRecursivo(elemento, raiz);
        }
    }

    //TDA Recorrer
    public String recorrerPreorden() {
        String resultado = "";
        if (!esVacio()) {
            StringBuilder sb = new StringBuilder();
            resultado = recorrerPreordenRecursivo(raiz, resultado);
            sb.append(resultado).delete(resultado.length() - 3, resultado.length());
            resultado = "[" + sb + "]";
        } else {
            resultado = "[ ]";
        }

        return resultado;
    }

    public String recorrerInorden() {
        String resultado = "";
        if (!esVacio()) {
            StringBuilder sb = new StringBuilder();
            resultado = recorrerInordenRecursivo(raiz, resultado);
            sb.append(resultado).delete(resultado.length() - 3, resultado.length());
            resultado = "[" + sb + "]";
        } else {
            resultado = "[ ]";
        }

        return resultado;
    }

    public String recorrerPostorden() {
        String resultado = "";
        if (!esVacio()) {
            StringBuilder sb = new StringBuilder();
            resultado = recorrerPostordenRecursivo(raiz, resultado);
            sb.append(resultado).delete(resultado.length() - 3, resultado.length());
            resultado = "[" + sb + "]";
        } else {
            resultado = "[ ]";
        }

        return resultado;
    }

    //TDA Existe dato
    public boolean existe(T elemento) {
        boolean existe = false;
        if (elemento != null) {
            existe = verificarExisteRecursivo(elemento, raiz, false);
        }

        return existe;
    }

    //TDA Obtener altura
    public int obtenerAltura() {
        int altura = 0;
        if (!esVacio()) {
            altura = obtenerAlturaRecursivo(raiz, altura);
        }

        return altura;
    }

    //TDA Contar hojas
    public int contarHojas() {
        int numeroHojas = 0;
        if (!esVacio()) {
            numeroHojas = contarHojasRecursivo(raiz, numeroHojas);
        }

        return numeroHojas;
    }

    //TDA Obtener amplitud
    public String obtenerAmplitud() {
        String amplitud = "";
        if (!esVacio()) {
            StringBuilder sb = new StringBuilder();
            Queue<NodoArbol<T>> colaNodos = new LinkedList<>();
            colaNodos.add(raiz);

            while (!colaNodos.isEmpty()) {
                NodoArbol<T> actual = colaNodos.poll();
                sb.append(actual.getElemento());

                if (actual.getIzquierda() != null) {
                    colaNodos.add(actual.getIzquierda());
                }

                if (actual.getDerecha() != null) {
                    colaNodos.add(actual.getDerecha());
                }

                if (!colaNodos.isEmpty()) {
                    sb.append(" - ");
                }
            }

            amplitud = "[" + sb + "]";
        } else {
            amplitud = "[ ]";
        }

        return amplitud;
    }

    //TDA Obtener menor
    public T obtenerMenor() {
        NodoArbol<T> actual = raiz;
        while (actual.getIzquierda() != null) {
            actual = actual.getIzquierda();
        }
        return actual.getElemento();
    }

    //TDA Obtener mayor
    public T obtenerMayor() {
        NodoArbol<T> actual = raiz;
        while (actual.getDerecha() != null) {
            actual = actual.getDerecha();
        }

        return actual.getElemento();
    }

    //TDA Borrar árbol
    public void borrarArbol() {
        raiz = null;
        peso = 0;
    }

    //TDA Obtener peso
    public int getPeso() {
        return peso;
    }

    //Private

    private void agregarNodoRecursivo(NodoArbol<T> nuevoNodo, NodoArbol<T> raiz) {
        if (raiz == null) {
            return;
        }

        if (nuevoNodo.getElemento().compareTo(raiz.getElemento()) < 0) {
            //Caso Base
            if (raiz.getIzquierda() == null) {
                raiz.setIzquierda(nuevoNodo);
                peso++;
                //Caso Recursivo
            } else {
                agregarNodoRecursivo(nuevoNodo, raiz.getIzquierda());
            }
        } else {
            //Caso Base
            if (raiz.getDerecha() == null) {
                raiz.setDerecha(nuevoNodo);
                peso++;
                //Caso Recursivo
            } else {
                agregarNodoRecursivo(nuevoNodo, raiz.getDerecha());
            }
        }
    }


    /**
     * TDA Eliminar
     * Casos:
     * 1. Es una Hoja
     * 2. Que tenga 1 hijo
     * 3. Que tenga 2 hijos
     */

    public void eliminarPrimero() {

        //Caso 3
        if (raiz.getIzquierda() != null && raiz.getDerecha() != null) {
            NodoArbol<T> nodoMayorIzquierda = obtenerNodoMayor(raiz.getIzquierda());
            eliminarRecursivo(nodoMayorIzquierda.getElemento(), raiz);
            raiz.setElemento(nodoMayorIzquierda.getElemento());

            //Caso 2
        } else if (raiz.getIzquierda() != null) {
            T elementoIzquierda = raiz.getIzquierda().getElemento();
            eliminarRecursivo(elementoIzquierda, raiz);
            raiz.setElemento(elementoIzquierda);

        } else if (raiz.getDerecha() != null) {
            T elementoDerecha = raiz.getDerecha().getElemento();
            eliminarRecursivo(elementoDerecha, raiz);
            raiz.setElemento(elementoDerecha);

            //Caso 1
        } else {
            borrarArbol();
        }
    }

    private void eliminarRecursivo(T elemento, NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }

        //Caso Base
        if (elemento.compareTo(actual.getElemento()) < 0) {
            if (actual.getIzquierda().getElemento().equals(elemento)) {

                //Caso 3
                NodoArbol<T> nodoAEliminar = actual.getIzquierda();
                if (nodoAEliminar.getIzquierda() != null && nodoAEliminar.getDerecha() != null) {
                    NodoArbol<T> nodoMayorIzquierda = obtenerNodoMayor(nodoAEliminar.getIzquierda());
                    eliminarRecursivo(nodoMayorIzquierda.getElemento(), nodoAEliminar);
                    nodoAEliminar.setElemento(nodoMayorIzquierda.getElemento());

                    //Caso 2
                } else if (nodoAEliminar.getIzquierda() != null) {
                    actual.setIzquierda(nodoAEliminar.getIzquierda());

                } else if (nodoAEliminar.getDerecha() != null) {
                    actual.setIzquierda(nodoAEliminar.getDerecha());

                    //Caso 1
                } else {
                    actual.setIzquierda(null);
                }
                return;
            }

            eliminarRecursivo(elemento, actual.getIzquierda());

        } else {
            if (actual.getDerecha().getElemento().equals(elemento)) {
                //Caso 3
                NodoArbol<T> nodoAEliminar = actual.getDerecha();
                if (nodoAEliminar.getIzquierda() != null && nodoAEliminar.getDerecha() != null) {
                    NodoArbol<T> nodoMayorIzquierda = obtenerNodoMayor(nodoAEliminar.getIzquierda());
                    eliminarRecursivo(nodoMayorIzquierda.getElemento(), nodoAEliminar);
                    nodoAEliminar.setElemento(nodoMayorIzquierda.getElemento());

                    //Caso 2
                } else if (nodoAEliminar.getIzquierda() != null) {
                    actual.setDerecha(nodoAEliminar.getIzquierda());

                } else if (nodoAEliminar.getDerecha() != null) {
                    actual.setDerecha(nodoAEliminar.getDerecha());

                    //Caso 1
                } else {
                    actual.setDerecha(null);
                }
                return;
            }

            eliminarRecursivo(elemento, actual.getDerecha());
        }
    }

    //TDA Obtener mayor
    private NodoArbol<T> obtenerNodoMayor(NodoArbol<T> actual) {
        while (actual.getDerecha() != null) {
            actual = actual.getDerecha();
        }

        return actual;
    }

    //TDA Obtener menor
    private NodoArbol<T> obtenerNodoMenor(NodoArbol<T> actual) {
        while (actual.getIzquierda() != null) {
            actual = actual.getIzquierda();
        }

        return actual;
    }

    private NodoArbol<T> buscarNodo(T elemento, NodoArbol<T> actual) {
        if (actual == null || !subarbol(actual).existe(elemento)) {
            return null;
        }

        if (actual.getElemento().equals(elemento)) {
            return actual;
        }

        if (actual.getElemento().compareTo(elemento) < 0) {
            buscarNodo(elemento, actual.getIzquierda());

        } else if (actual.getElemento().compareTo(elemento) > 0) {
            buscarNodo(elemento, actual.getDerecha());

        }

        return null;
    }

    private ArbolBinarioABB<T> subarbol(NodoArbol<T> raiz) {
        return new ArbolBinarioABB<>(raiz);
    }

    //TDA Obtener peso
    private int obtenerPesoSubarbol(NodoArbol<T> raiz, int peso) {
        if (raiz == null) {
            return peso;
        }

        if (raiz.getIzquierda() == null && raiz.getDerecha() == null) {
            return 1;
        }

        int pesoIzquierdo = obtenerPesoSubarbol(raiz.getIzquierda(), peso);
        int pesoDerecho = obtenerPesoSubarbol(raiz.getDerecha(), peso);

        peso += 1 + pesoIzquierdo + pesoDerecho;
        return peso;
    }

    private String recorrerInordenRecursivo(NodoArbol<T> raiz, String resultado) {

        if (raiz == null) {
            return "";
        }

        if (raiz.esHoja()) {
            return raiz.getElemento().toString() + " - ";
        }

        String subarbolIzquierdo = recorrerInordenRecursivo(raiz.getIzquierda(), resultado);
        String subarbolDerecho = recorrerInordenRecursivo(raiz.getDerecha(), resultado);

        resultado += subarbolIzquierdo + raiz.getElemento() + " - " + subarbolDerecho;
        return resultado;
    }

    private String recorrerPreordenRecursivo(NodoArbol<T> raiz, String resultado) {

        if (raiz == null) {
            return "";
        }

        if (raiz.esHoja()) {
            return raiz.getElemento().toString() + " - ";
        }

        String subarbolIzquierdo = recorrerPreordenRecursivo(raiz.getIzquierda(), resultado);
        String subarbolDerecho = recorrerPreordenRecursivo(raiz.getDerecha(), resultado);

        resultado += raiz.getElemento() + " - " + subarbolIzquierdo + subarbolDerecho;
        return resultado;
    }

    private String recorrerPostordenRecursivo(NodoArbol<T> raiz, String resultado) {

        if (raiz == null) {
            return "";
        }

        if (raiz.esHoja()) {
            return raiz.getElemento().toString() + " - ";
        }

        String subarbolIzquierdo = recorrerPostordenRecursivo(raiz.getIzquierda(), resultado);
        String subarbolDerecho = recorrerPostordenRecursivo(raiz.getDerecha(), resultado);

        resultado += subarbolIzquierdo + subarbolDerecho + raiz.getElemento() + " - ";
        return resultado;
    }

    private boolean verificarExisteRecursivo(T elemento, NodoArbol<T> raiz, boolean resultado) {
        if (raiz == null) {
            return false;
        }

        if (raiz.getElemento().equals(elemento)) {
            return true;
        }

        boolean resuladoIzquierda = verificarExisteRecursivo(elemento, raiz.getIzquierda(), resultado);
        boolean resultadoDerecha = verificarExisteRecursivo(elemento, raiz.getDerecha(), resultado);

        return resuladoIzquierda || resultadoDerecha;
    }

    private int obtenerAlturaRecursivo(NodoArbol<T> raiz, int altura) {
        if (raiz == null) {
            return altura;
        }

        if (raiz.esHoja()) {
            return altura + 1;
        }

        int alturaIzquierda = obtenerAlturaRecursivo(raiz.getIzquierda(), altura + 1);
        int alturaDerecha = obtenerAlturaRecursivo(raiz.getDerecha(), altura + 1);

        return Math.max(alturaIzquierda, alturaDerecha);
    }

    private int contarHojasRecursivo(NodoArbol<T> raiz, int numeroHojas) {
        if (raiz == null) {
            return numeroHojas;
        }

        if (raiz.esHoja()) {
            return numeroHojas + 1;
        }

        int alturaIzquierda = contarHojasRecursivo(raiz.getIzquierda(), numeroHojas);
        int alturaDerecha = contarHojasRecursivo(raiz.getDerecha(), numeroHojas);

        return alturaIzquierda + alturaDerecha;
    }
}