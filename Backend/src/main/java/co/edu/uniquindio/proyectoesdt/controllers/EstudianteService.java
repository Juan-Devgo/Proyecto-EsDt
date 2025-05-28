package co.edu.uniquindio.proyectoesdt.controllers;

import co.edu.uniquindio.proyectoesdt.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Estudiante autenticar(String nickname, String contrasenia) {
        return estudianteRepository.findByNicknameAndContrasenia(nickname, contrasenia);
    }
}