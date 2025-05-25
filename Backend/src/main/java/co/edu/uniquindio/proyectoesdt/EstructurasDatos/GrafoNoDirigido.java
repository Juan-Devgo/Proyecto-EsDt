package co.edu.uniquindio.proyectoesdt.EstructurasDatos;

import co.edu.uniquindio.proyectoesdt.Usuario;

import java.util.*;

public class GrafoNoDirigido<T extends Usuario> implements Iterable<T> {
    private int tamanio;
    private final ArrayList<NodoGrafo<T>> usuarios;

    public GrafoNoDirigido() {
        tamanio = 0;
        usuarios = new ArrayList<>();
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorGrafoNoDirigido<>(usuarios.iterator());
    }

    public ArrayList<NodoGrafo<T>> getUsuarios() {
        return usuarios;
    }

    //TDA Verificar es vacío
    public boolean esVacio() {
        return usuarios.isEmpty();
    }

    //TDA Verificar existe elemento
    public boolean existe(T usuario) {
        boolean existe = false;
        if (usuario != null) {
            for (NodoGrafo<T> u : usuarios) {
                if (u.getElemento().equals(usuario)) {
                    existe = true;
                    break;
                }
            }
        }

        return existe;
    }

    public boolean existeConexion(T usuario1, T usuario2) {
        boolean existe = false;

        if (existe(usuario1) && existe(usuario2)) {
            if ((!usuario1.equals(usuario2))) {
                Optional<NodoGrafo<T>> nodoOptional1 = buscarNodo(usuario1);
                Optional<NodoGrafo<T>> nodoOptional2 = buscarNodo(usuario2);

                if (nodoOptional1.isPresent() && nodoOptional2.isPresent()) {
                    existe = nodoOptional1.get().existeNodo(nodoOptional2.get());
                }
            }
        }

        return existe;
    }

    public boolean existeCamino(T usuario1, T usuario2) {
        boolean existe = false;

        if (existe(usuario1) && existe(usuario2)) {
            if ((!usuario1.equals(usuario2))) {
                Optional<NodoGrafo<T>> nodoOptional1 = buscarNodo(usuario1);
                Optional<NodoGrafo<T>> nodoOptional2 = buscarNodo(usuario2);

                if (nodoOptional1.isPresent() && nodoOptional2.isPresent()) {
                    existe = nodoOptional1.get().existeCamino(nodoOptional2.get());
                }
            }
        }

        return existe;
    }

    //TDA Insersión
    public void agregar(T usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Se ha intentado agregar un usuario nulo.");
        }

        if (!existe(usuario)) {
            NodoGrafo<T> nuevoNodo = new NodoGrafo<>(usuario);
            usuarios.add(nuevoNodo);
            tamanio++;
        }
    }

    //Agregar conexiones
    public void conectar(T usuario1, T usuario2) {
        if (existe(usuario1) && existe(usuario2)) {
            if ((!usuario1.equals(usuario2))) {
                Optional<NodoGrafo<T>> nodoOptional1 = buscarNodo(usuario1);
                Optional<NodoGrafo<T>> nodoOptional2 = buscarNodo(usuario2);

                if (nodoOptional1.isPresent() && nodoOptional2.isPresent()) {
                    nodoOptional1.get().agregarNodo(nodoOptional2.get());

                } else {
                    throw new RuntimeException("Error inesperado: no se obtuvieron los nodos a conectar.");
                }
            }
        }
    }

    //TDA Eliminación
    public void eliminar(T usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Se ha intentado eliminar un usuario nulo.");
        }

        if (existe(usuario)) {
            Optional<NodoGrafo<T>> nodoOptional = buscarNodo(usuario);

            if (nodoOptional.isPresent()) {
                NodoGrafo<T> nodoAEliminar = nodoOptional.get();
                for (NodoGrafo<T> adyacente : nodoAEliminar.getAdyacentes()) {
                    nodoAEliminar.eliminarNodo(adyacente);
                }
                tamanio--;
            } else {
                throw new RuntimeException("Error inesperado: no se obtuvo el nodo a eliminar.");
            }
        }
    }

    //TDA Buscar

    //Buscar camino más corto
    public String encontrarCaminoCorto(T usuario1, T usuario2) {
        StringBuilder camino = new StringBuilder();
        int distancia = -1;

        if (usuario1 != null && usuario2 != null) {
            if (existe(usuario1) && existe(usuario2)) {
                if (!usuario1.equals(usuario2)) {

                    int[][] matriz = obtenerMatrizAdyacencia();
                    Optional<NodoGrafo<T>> nodoOptional1 = buscarNodo(usuario1);
                    Optional<NodoGrafo<T>> nodoOptional2 = buscarNodo(usuario2);
                    if (nodoOptional1.isPresent() && nodoOptional2.isPresent()) {
                        int origen = usuarios.indexOf(nodoOptional1.get());
                        int objetivo = usuarios.indexOf(nodoOptional2.get());
                        String resultadoDijkstra = Dijkstra.dijkstraConRuta(matriz, origen, objetivo);


                        if (resultadoDijkstra.charAt(0) != '∞') {
                            int i = 0;
                            while (resultadoDijkstra.charAt(i) != '%') {
                                i++;
                            }
                            distancia = Integer.parseInt(resultadoDijkstra.substring(0, i));
                            i++;

                            int k;
                            LinkedList<T> ruta = new LinkedList<>();
                            for (; i < resultadoDijkstra.length(); i++) {
                                int j = i;
                                while (resultadoDijkstra.charAt(i) != '-') {
                                    i++;
                                }
                                k = i;
                                ruta.add(usuarios.get(Integer.parseInt(resultadoDijkstra.substring(j, k)))
                                        .getElemento());
                            }

                            camino.append("El camino más corto es: ");
                            for (T nodo : ruta) {
                                camino.append(nodo.toString()).append(" -> ");
                            }
                            camino.setLength(camino.length() - 4); //Eliminar el último " -> "

                            camino.append(". ").append("Distancia total: ").append(distancia);

                        } else {
                            camino.append("No hay forma de llegar a ").append(usuarios.get(objetivo)).append(" desde ")
                                    .append(usuarios.get(origen)).append(".");
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Se ha intentado encontrar la ruta mínima entre dos elementos nulos");
        }

        return camino.toString();
    }

    //Obtener distancia mínima
    public int encontrarDistanciaMinima(T usuario1, T usuario2) {
        int distancia = -1;

        if (usuario1 != null && usuario2 != null) {
            if (existe(usuario1) && existe(usuario2)) {
                if (!usuario1.equals(usuario2)) {

                    int[][] matriz = obtenerMatrizAdyacencia();
                    Optional<NodoGrafo<T>> nodoOptional1 = buscarNodo(usuario1);
                    Optional<NodoGrafo<T>> nodoOptional2 = buscarNodo(usuario2);
                    if (nodoOptional1.isPresent() && nodoOptional2.isPresent()) {
                        int origen = usuarios.indexOf(nodoOptional1.get());
                        int objetivo = usuarios.indexOf(nodoOptional2.get());
                        String resultadoDijkstra = Dijkstra.dijkstra(matriz, origen);

                        int j;
                        int k;
                        for (int i = 0; i < resultadoDijkstra.length(); i++) {
                            if (resultadoDijkstra.charAt(i) == '%') {
                                i++;
                                j = i;
                                while (resultadoDijkstra.charAt(i) != ':') {
                                    i++;
                                }
                                k = i;
                                if (Integer.parseInt(resultadoDijkstra.substring(j, k)) == objetivo) {
                                    i++;
                                    j = i;
                                    while (resultadoDijkstra.charAt(i) != '/') {
                                        i++;
                                    }
                                    k = i;
                                    String distanciaString = resultadoDijkstra.substring(j, k);
                                    if (!distanciaString.equals("∞")) {
                                        distancia = Integer.parseInt(distanciaString);
                                    }
                                }
                            }
                        }

                    } else {
                        throw new RuntimeException("Error inesperado: No se obtuvieron los elementos para calcular " +
                                "su distancia mínima");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Se ha intentado calcular la distancia mínima entre dos elementos " +
                    "nulos");
        }

        return distancia;
    }

    //Métodos Privados

    //Buscar nodo
    private Optional<NodoGrafo<T>> buscarNodo(T elemento) {
        return usuarios.stream().filter(u -> u.getElemento().equals(elemento)).findFirst();
    }

    public int[][] obtenerMatrizAdyacencia() {
        int[][] matrizAdyacencia = new int[tamanio][tamanio];

        for (int fila = 0; fila < tamanio; fila++) {
            for (int columna = 0; columna < tamanio; columna++) {
                if (usuarios.get(fila).existeNodo(usuarios.get(columna))) {
                    matrizAdyacencia[fila][columna] = 1;
                } else {
                    matrizAdyacencia[fila][columna] = 0;
                }
            }
        }

        return matrizAdyacencia;
    }

    //Algoritmo de Dijkstra para encontrar caminos más cortos entre nodos del grafo.

    private class Dijkstra {
        public static String dijkstra(int[][] matriz, int origen) {
            int n = matriz.length;
            int[] distancias = new int[n];
            boolean[] visitados = new boolean[n];

            Arrays.fill(distancias, Integer.MAX_VALUE);
            distancias[origen] = 0;

            for (int i = 0; i < n; i++) {

                int nodoDistanciaMinima = encontrarDistanciaMinima(distancias, visitados);
                visitados[nodoDistanciaMinima] = true;

                for (int v = 0; v < n; v++) {
                    if (!visitados[v] && matriz[nodoDistanciaMinima][v] != 0 &&
                            distancias[nodoDistanciaMinima] != Integer.MAX_VALUE &&
                            distancias[nodoDistanciaMinima] + matriz[nodoDistanciaMinima][v] < distancias[v]) {
                        distancias[v] = distancias[nodoDistanciaMinima] + matriz[nodoDistanciaMinima][v];
                    }
                }
            }

            return obtenerResultado(distancias, origen);
        }

        public static String dijkstraConRuta(int[][] matriz, int origen, int objetivo) {
            int n = matriz.length;
            int[] distancias = new int[n];
            boolean[] visitado = new boolean[n];
            int[] predecesores = new int[n];

            Arrays.fill(distancias, Integer.MAX_VALUE);
            Arrays.fill(predecesores, -1);
            distancias[origen] = 0;

            for (int i = 0; i < n - 1; i++) {
                int nodoDistanciaMinima = encontrarDistanciaMinima(distancias, visitado);
                visitado[nodoDistanciaMinima] = true;

                for (int v = 0; v < n; v++) {
                    if (!visitado[v] && matriz[nodoDistanciaMinima][v] != 0 &&
                            distancias[nodoDistanciaMinima] != Integer.MAX_VALUE &&
                            distancias[nodoDistanciaMinima] + matriz[nodoDistanciaMinima][v] < distancias[v]) {
                        distancias[v] = distancias[nodoDistanciaMinima] + matriz[nodoDistanciaMinima][v];
                        predecesores[v] = nodoDistanciaMinima;
                    }
                }
            }

            // Reconstruir la ruta
            List<Integer> ruta = obtenerRuta(predecesores, objetivo);

            // Formatear el resultado
            StringBuilder sb = new StringBuilder();
            if (distancias[objetivo] != Integer.MAX_VALUE) {
                sb.append(distancias[objetivo]).append("%");
                for (int nodo : ruta) {
                    sb.append(nodo).append("-");
                }
            } else {
                sb.append("∞");
            }
            return sb.toString();
        }

        private static int encontrarDistanciaMinima(int[] distancias, boolean[] visitados) {
            int min = Integer.MAX_VALUE;
            int nodoDistanciaMinima = -1;

            for (int i = 0; i < distancias.length; i++) {
                if (!visitados[i] && distancias[i] <= min) {
                    min = distancias[i];
                    nodoDistanciaMinima = i;
                }
            }

            return nodoDistanciaMinima;
        }

        private static String obtenerResultado(int[] distancias, int origen) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < distancias.length; i++) {
                if (distancias[i] != Integer.MAX_VALUE) {
                    sb.append("%").append(i).append(":").append(distancias[i]);
                } else {
                    sb.append("%").append(i).append(":").append("∞");
                }
                sb.append("/");
            }

            return sb.toString();
        }

        public static List<Integer> obtenerRuta(int[] predecesores, int objetivo) {
            List<Integer> ruta = new ArrayList<>();
            int actual = objetivo;

            // Retrocedemos desde el objetivo hasta el origen
            while (actual != -1) {
                ruta.add(actual);
                actual = predecesores[actual];
            }

            // Invertimos la lista para obtener el orden correcto (origen -> destino)
            Collections.reverse(ruta);

            return ruta;
        }
    }
}
