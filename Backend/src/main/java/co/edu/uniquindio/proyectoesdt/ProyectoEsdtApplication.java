package co.edu.uniquindio.proyectoesdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class ProyectoEsdtApplication {

    public static Connection conectarBD() {
        Connection connection;
        String host = "jdbc:mysql://localhost:3306/";
        String usuario = "root";
        String contrasenia = "";
        String BD = "proyecto-esdt-BD";
        try {
            System.out.println("Estableciendo conexión con la base de datos...");
            connection = DriverManager.getConnection(host + BD, usuario, contrasenia);
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error de conexión con la base de datos...");
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void desconectarBD(Connection bd) {
        try {
            bd.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ProyectoEsdtApplication.class, args);
        Connection connection = conectarBD();
    }

}
