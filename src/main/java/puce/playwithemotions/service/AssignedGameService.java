package puce.playwithemotions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.AssignedGame;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.Game;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.AssignedGameRepository;
import puce.playwithemotions.repository.CourseRepository;
import puce.playwithemotions.repository.GameRepository;
import puce.playwithemotions.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignedGameService {

    private final AssignedGameRepository assignedGameRepository;
    private final CourseRepository courseRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public AssignedGameService(
            AssignedGameRepository assignedGameRepository,
            CourseRepository courseRepository,
            GameRepository gameRepository,
            UserRepository userRepository) {
        this.assignedGameRepository = assignedGameRepository;
        this.courseRepository = courseRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    // 📌 Asignar un juego a un curso (verifica si ya existe)
    public AssignedGame assignGameToCourse(UUID profesorId, UUID courseId, UUID gameId) {
        User profesor = userRepository.findById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("El profesor no existe"));

        Course curso = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("El curso no existe"));

        Game juego = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("El juego no existe"));

        // Verificar si el juego ya está asignado al curso
        boolean exists = assignedGameRepository.existsByCursoAndJuego(curso, juego);
        if (exists) {
            throw new IllegalArgumentException("Este juego ya está asignado a este curso.");
        }

        AssignedGame assignedGame = new AssignedGame();
        assignedGame.setProfesor(profesor);
        assignedGame.setCurso(curso);
        assignedGame.setJuego(juego);
        assignedGame.setFechaAsignacion(LocalDateTime.now());

        return assignedGameRepository.save(assignedGame);
    }

    // 📌 Desasignar un juego de un curso
    public void unassignGameFromCourse(UUID courseId, UUID gameId) {
        Course curso = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("El curso no existe"));

        Game juego = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("El juego no existe"));

        AssignedGame assignedGame = assignedGameRepository.findByCursoAndJuego(curso, juego)
                .orElseThrow(() -> new IllegalArgumentException("El juego no está asignado a este curso."));

        assignedGameRepository.delete(assignedGame);
    }


    // 📌 Obtener juegos asignados a un curso
    public List<AssignedGame> getGamesByCourse(UUID courseId) {
        Course curso = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("El curso no existe"));

        return assignedGameRepository.findByCurso(curso);
    }

    // 📌 Obtener juegos asignados por un profesor
    public List<AssignedGame> getGamesByProfesor(UUID profesorId) {
        if (!userRepository.existsById(profesorId)) {
            throw new IllegalArgumentException("El profesor no existe");
        }
        return assignedGameRepository.findByProfesorId(profesorId);
    }
}



