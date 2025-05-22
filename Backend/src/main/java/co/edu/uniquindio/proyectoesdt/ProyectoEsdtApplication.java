package co.edu.uniquindio.proyectoesdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class ProyectoEsdtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoEsdtApplication.class, args);
    }

}
