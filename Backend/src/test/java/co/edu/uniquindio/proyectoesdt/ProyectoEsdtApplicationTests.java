package co.edu.uniquindio.proyectoesdt;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProyectoEsdtApplicationTests {
    private MiLinkedList<String> listaSimple;
    private MiCola<String> cola;
    private GrupoEstudio grupoEstudio;
    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Estudiante estudiante3;

    @BeforeEach
        //Instanciación de las estructuras
    void creacionEstructurasDatos() {
        listaSimple = new MiLinkedList<>();
        cola = new MiCola<>();
        grupoEstudio = new GrupoEstudio("Estructuras de Datos");
        estudiante1 = new Estudiante("Angie", "angie123", "1234", "Ingeniería de " +
                "Sistemas");
        estudiante2 = new Estudiante("Amyi", "amyi123", "1254", "Ingeniería de " +
                "Sistemas");
        estudiante3 = new Estudiante("JuanDi", "ElMaravillosoNovioDeAngie", "amo a mi novia",
                "Ingeniería de Sistemas");
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

    //Prueba Moderador: Creacion de un Moderador
    void probarCreacionModerador() {
        Moderador moderador = new Moderador("Grisesito", "GrisesitoAdmin", "mehe",
                "Ingenieria en alimentos");

        assertNotNull(moderador);
        assertEquals("Grisesito", moderador.getNombre());
        assertEquals("GrisesitoAdmin", moderador.getNickname());
        assertEquals("mehe", moderador.getContrasenia());
        assertEquals("Ingenieria en alimentos", moderador.getCarrera());
    }

    //Prueba Estudiante: Creacion de un Estudiante
    void probarCreacionEstudiante() {
        Estudiante estudiante = new Estudiante("Manchitas", "manchititicas", "meow",
                "Ingenieria civil");

        assertNotNull(estudiante);
        assertEquals("Manchitas", estudiante.getNombre());
        assertEquals("manchititicas", estudiante.getNickname());
        assertEquals("meow", estudiante.getContrasenia());
        assertEquals("Ingenieria civil", estudiante.getCarrera());
    }

    //Prueba Grupo de estudio: Creacion Grupo de estudio
    void probarCreacionGrupoEstudio() {

        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio(""));
        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio(null));
        assertEquals("Estructuras de Datos", grupoEstudio.getNombre());
        assertTrue(grupoEstudio.getTemas().isEmpty());
        assertTrue(grupoEstudio.getSolicitudes().isEmpty());
        assertTrue(grupoEstudio.getIntegrantes().isEmpty());
    }

    //Prueba Grupo de estudio: Recibir solicitudes de estudiantes y agregarlas
    void probarRecepcionSolictudesGrupo() {
        grupoEstudio.recibirSolicitud(estudiante1);
        grupoEstudio.recibirSolicitud(estudiante2);
        grupoEstudio.recibirSolicitud(estudiante3);

        assertTrue(grupoEstudio.getSolicitudes().contains(estudiante1));
        assertTrue(grupoEstudio.getSolicitudes().contains(estudiante2));
        assertTrue(grupoEstudio.getSolicitudes().contains(estudiante3));
    }

    //Prueba Grupo de estudio: Agregar aceptar y agregar un integrante
    void probarAgregarIntegranteGrupo() {
        grupoEstudio.recibirSolicitud(estudiante1);
        grupoEstudio.aceptarSolicitud(estudiante1);

        assertFalse(grupoEstudio.getSolicitudes().contains(estudiante1));
    }

    //Prueba Publicacion: Creacion de publicacion
    void probarCreacionPublicacion() {
        ArrayList<String> parrafos = new ArrayList<>();
        ArrayList<File> archivos = new ArrayList<>();
        archivos.add(new File("Meopw"));
        parrafos.add("Un dia la vaca loca se cayo y se le torcio la cola");
        String titulo = "Dinopardo";
        Contenido contenido = new Contenido(titulo, archivos, parrafos);
        Publicacion anuncio = new Publicacion(titulo, "dinos", estudiante1, TipoPublicacion.ANUNCIO, contenido);
        assertNotNull(anuncio);
    }

    //Prueba Publicacion: Creacion publicacion con tipoPublicacion Solicitud de ayuda
    void probarCracionPublicacionTipoSolicitud() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayList<String> parrafos = new ArrayList<>();
            ArrayList<File> archivos = new ArrayList<>();
            archivos.add(new File("Meopw"));
            parrafos.add("Un dia la vaca loca se cayo y se le torcio la cola");
            String titulo = "Título";
            Contenido contenido = new Contenido(titulo, archivos, parrafos);
            new Publicacion(titulo, "Tema", estudiante1, TipoPublicacion.SOLICITUD_AYUDA, contenido);
        });
    }

    //Prueba Solicitud Ayuda: Creacion Solicitud de Ayuda desde la clase SolucitudAyuda
    void probarCreacionSolicitudAyuda() {
        ArrayList<String> parrafos = new ArrayList<>();
        ArrayList<File> archivos = new ArrayList<>();
        archivos.add(new File("Meopw"));
        parrafos.add("Un dia la vaca loca se cayo y se le torcio la cola");
        String titulo = "Necesito ayuda";
        Contenido contenido = new Contenido(titulo, archivos, parrafos);
        SolicitudAyuda solicitud = new SolicitudAyuda(titulo, "Teoría de grafos", estudiante2, contenido,
                true, Prioridad.ALTA);

        assertNotNull(solicitud);
        assertTrue(solicitud.isActiva());
    }

}

