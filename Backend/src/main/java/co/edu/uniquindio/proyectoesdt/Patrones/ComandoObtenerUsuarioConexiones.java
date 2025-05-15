package co.edu.uniquindio.proyectoesdt.Patrones;

import co.edu.uniquindio.proyectoesdt.EstructurasDatos.ListaPrioridad;
import co.edu.uniquindio.proyectoesdt.EstructurasDatos.MiLinkedList;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Usuario;
public class ComandoObtenerUsuarioConexiones implements ComandoModerador{

    private Moderador moderador;

    public ComandoObtenerUsuarioConexiones(Moderador moderador) {
        this.moderador = moderador;
    }

    @Override
    public void ejecutar() {
        ListaPrioridad<Usuario> usuarios = moderador.obtenerUsuariosMasConexiones();
        System.out.println("Usuarios con más conexiones:");
        for (Usuario u : usuarios) {
            System.out.println(u.getNickname() + " - " + u.getCantidadAmigos() + " conexiones");
        }
    }

}
