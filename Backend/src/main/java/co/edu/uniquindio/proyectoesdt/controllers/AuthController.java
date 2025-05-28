package co.edu.uniquindio.proyectoesdt.controllers;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import co.edu.uniquindio.proyectoesdt.Moderador;
import co.edu.uniquindio.proyectoesdt.Plataforma;
import co.edu.uniquindio.proyectoesdt.Usuario;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080") // Ajusta según tu puerto frontend
public class AuthController {

    private final Plataforma plataforma;

    public AuthController() {
        this.plataforma = Plataforma.getInstancia();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        Usuario usuario = plataforma.buscarEstudiante(request.getUsername());
        if (usuario == null) {
            usuario = plataforma.buscarModerador(request.getUsername());
        }

        if (usuario != null && usuario.getContrasenia().equals(request.getPassword())) {
            if (usuario.getActivo()) {
                Map<String, Object> response = new HashMap<>();
                response.put("nombre", usuario.getNombre());
                response.put("nickname", usuario.getNickname());
                response.put("carrera", usuario.getCarrera());
                response.put("activo", usuario.getActivo());
                response.put("tipo", usuario instanceof Moderador ? "moderador" : "estudiante");

                return response;
            } else {
                throw new RuntimeException("Usuario baneado");
            }
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}