package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ProyectoEsdtApplicationTests {
    private MiLinkedList<String> listaSimple;
    private MiCola<String> cola;


    @BeforeEach //Instanciación de las estructuras
    void creacionEstructurasDatos() {
        listaSimple = new MiLinkedList<>();

        cola = new MiCola<>();

    }

    @Test
    void probarFuncionalidadesEstructurasDatos() {

        listaSimple.agregar("1");
        cola.encolarCola("1");

        listaSimple.agregarAlInicio("2");
        cola.encolarCola("2");


        listaSimple.agregarEnPosicion("3", 3);
        cola.encolarCola("2");

        probarInsesrion();
        probarObtencion();
        probarMuestraDatos();
        probarEliminacion();
        probarBusqueda();
    }

    //Prueba TDA: Insersión
    void probarInsesrion() {
        //Lista simplemente enlazada
        assertTrue(listaSimple.contiene("1"));
        assertTrue(listaSimple.contiene("2"));
        assertTrue(listaSimple.contiene("3"));

        //Lista circular simplemente enlazada

    }

    //Prueba TDA: Obtención
    void probarObtencion() {

    }

    //Prueba TDA: Muestra de Datos
    void probarMuestraDatos() {

    }

    //Prueba TDA: Eliminación
    void probarEliminacion() {

    }

    //Prueba TDA: Búsqueda
    void probarBusqueda() {

    }
}