package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.InsertableBD;

import java.util.Collection;

public interface DataAccessObject<T extends InsertableBD> {
    void insertar(Collection<T> insertables);
    Collection<T> leer();
    void eliminar (Collection<T> eliminables);
}
