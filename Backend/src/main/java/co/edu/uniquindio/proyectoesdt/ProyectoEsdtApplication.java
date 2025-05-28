package co.edu.uniquindio.proyectoesdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class ProyectoEsdtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoEsdtApplication.class, args);
        Plataforma plataforma = Plataforma.getInstancia();
        plataforma.leerBD();

        Moderador mod = plataforma.buscarModerador("Juanito_Golondrina");
        Estudiante juan = plataforma.buscarEstudiante("Juan-Devgo");
        Estudiante angiec = plataforma.buscarEstudiante("AngieC-eratosaurio");
        Estudiante angiek = plataforma.buscarEstudiante("AngieKB");
        Estudiante juanse = plataforma.buscarEstudiante("JuanSe");

        Publicacion publicacion1 = plataforma.buscarPublicacion("¡Primera Publicación!");

        GrupoEstudio grupo1 = plataforma.buscarGrupoEstudio("Primer Grupo");
        GrupoEstudio comunidad = plataforma.buscarGrupoEstudio("Comunidad épica de JuanSe");

        plataforma.generarGruposEstudio();

        for(Usuario u: plataforma.getUsuarios()) {
            System.out.println(u);
        }
        System.out.println(Arrays.deepToString(plataforma.generarGrafo()));
        plataforma.generarConexionesEstudiantes();
        System.out.println(Arrays.deepToString(plataforma.generarGrafo()));

        System.out.println(plataforma.obtenerSugerenciasUsuarios(juan));

        System.out.println(plataforma.obtenerSugerenciasUsuarios(juanse));

        System.out.println(plataforma.obtenerSugerenciasGrupos(juan));
        System.out.println(plataforma.obtenerSugerenciasGrupos(mod));

        plataforma.darLikePublicacion(publicacion1, angiek);
        plataforma.darLikePublicacion(publicacion1, angiec);

        plataforma.darComentarioPublicacion(publicacion1, juanse, "¡Felicidades!");
    }
}