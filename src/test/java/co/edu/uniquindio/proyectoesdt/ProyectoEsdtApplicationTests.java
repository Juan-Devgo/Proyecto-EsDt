package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProyectoEsdtApplicationTests {
    private MiLinkedList<String> listaSimple;
    private MiCola<String> cola;


    @BeforeEach //Instanciación de las estructuras
    void creacionEstructurasDatos() {
        listaSimple = new MiLinkedList<>();
        cola = new MiCola<>();
        //autor = new Estudiante("candy", "camdipam", "2313","gatologa");
    }

    @Test
    void probarFuncionalidadesEstructurasDatos() {

        listaSimple.agregar("1");
        listaSimple.agregarAlInicio("2");
        listaSimple.agregarEnPosicion("3", 3);
        listaSimple.agregarEnPosicion("4", 4);

        cola.encolarCola("1");
        cola.encolarCola("2");
        cola.encolarCola("3");

        probarInsercion();

        probarObtencion();

        probarMuestraDatos();

        probarBusqueda();

        probarEliminacion();

    }

    //Prueba TDA: Inserción
    void probarInsercion() {
        //Lista simplemente enlazada
        assertFalse(listaSimple.esVacia());
        assertTrue(listaSimple.contiene("1"));
        assertTrue(listaSimple.contiene("2"));
        assertTrue(listaSimple.contiene("3"));
        assertTrue(listaSimple.contiene("4"));
        //assertThrows(RuntimeException.class,);

        //Lista circular simplemente enlazada

    }

    //Prueba TDA: Obtención
    void probarObtencion() {
        //Lista simplemente enlazada
        assertEquals("2", listaSimple.obtener(2));
        assertEquals("4", listaSimple.obtener(4));
        assertEquals("3", listaSimple.obtenerUltimo());
        assertEquals("1", listaSimple.obtenerPrimero());

        //Cola
        assertEquals("1", cola.obtenerCabeza());

    }

    //Prueba TDA: Muestra de Datos
    void probarMuestraDatos() {
        //Lista simplemente enlazada
        assertEquals("[1, 2, 3, 4]", listaSimple.toString());

        //Cola
        assertEquals("[1, 2, 3]", cola.toString());
    }

    //Prueba TDA: Búsqueda
    void probarBusqueda() {
        //Lista simplemente enlazada
        assertEquals(1, listaSimple.buscar("1"));
        assertEquals(2, listaSimple.buscar("2"));
        assertEquals(3, listaSimple.buscar("3"));
        assertEquals(3, listaSimple.buscar("4"));

    }

    //Prueba TDA: Eliminación
    void probarEliminacion() {
        //Lista simplemente enlazada
        listaSimple.eliminar("3");
        assertFalse(listaSimple.contiene("3"));
        listaSimple.eliminarPosicion(2);
        assertFalse(listaSimple.contiene("2"));
        listaSimple.eliminarUltimo();
        assertFalse(listaSimple.contiene("4"));
        listaSimple.eliminarPrimero();
        assertFalse(listaSimple.contiene("1"));

        //Cola
        cola.desencolarCabeza();
        assertEquals("2", cola.obtenerCabeza());
        assertEquals("2", cola.sacarCabeza());
    }

    //Prueba: Creacion de un Moderador
    void probarCreacionModerador(){
        Moderador moderador = new Moderador("Grisesito","GrisesitoAdmin","mehe","Ingenieria en alimentos");

        assertNotNull(moderador);
        assertEquals("Grisesito", moderador.getNombre());
        assertEquals("GrisesitoAdmin", moderador.getNickname());
        assertEquals("mehe", moderador.getContrasenia());
        assertEquals("Ingenieria en alimentos",moderador.getCarrera() );
    }
}