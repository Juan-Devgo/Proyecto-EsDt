package co.edu.uniquindio.proyectoesdt.controllers;

import co.edu.uniquindio.proyectoesdt.Estudiante;


public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Estudiante findByNicknameAndContrasenia(String nickname, String contrasenia);
}
