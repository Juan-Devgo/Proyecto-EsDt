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

        Moderador mod = plataforma.buscarModerador("JuanitoGolondrina");
        Estudiante juan = plataforma.buscarEstudiante("Juan-Devgo");
        Estudiante angiec = plataforma.buscarEstudiante("AngieC-eratosaurio");
        Estudiante angiek = plataforma.buscarEstudiante("AngieKB");
        Estudiante juanse = plataforma.buscarEstudiante("JuanSe");

        GrupoEstudio grupo1 = plataforma.buscarGrupoEstudio("Primer Grupo");
        plataforma.crearComunidades();

        for(Usuario u: plataforma.getUsuarios()) {
            System.out.println(u);
        }
        System.out.println(Arrays.deepToString(plataforma.generarGrafo()));
        plataforma.generarConexionesEstudiantes();
        System.out.println(Arrays.deepToString(plataforma.generarGrafo()));
    }
}