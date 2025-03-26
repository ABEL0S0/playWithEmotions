package puce.playwithemotions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.AssignedGame;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.Game;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignedGameService {

    private final AssignedGameRepository assignedGameRepository;
    private final CourseRepository courseRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final StudentProgressRepository studentProgressRepository;


    @Autowired
    public AssignedGameService(
            AssignedGameRepository assignedGameRepository,
            CourseRepository courseRepository,
            GameRepository gameRepository,
            UserRepository userRepository, StudentProgressRepository studentProgressRepository) {
        this.assignedGameRepository = assignedGameRepository;
        this.courseRepository = courseRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.studentProgressRepository = studentProgressRepository;
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

    public List<AssignedGame> getUnlockedGames(UUID studentId, UUID courseId) {
        // Obtener juegos ordenados
        List<AssignedGame> juegos = assignedGameRepository.findByCursoIdOrderByOrdenAsc(courseId);

        // Verificar si el curso es progresivo
        boolean esProgresivo = juegos.get(0).getCurso().isProgresivo();

        if (!esProgresivo) {
            return juegos; // Si no es progresivo, devolver todos los juegos
        }

        // Obtener el último juego completado por el estudiante
        int ultimoJuegoCompletado = studentProgressRepository.findLastCompletedGameOrder(studentId, courseId).orElse(0);

        // Devolver juegos hasta el siguiente no completado
        return juegos.stream()
                .filter(juego -> juego.getOrden() <= ultimoJuegoCompletado + 1)
                .collect(Collectors.toList());
    }

    public Optional<AssignedGame> getNextGameForStudent(UUID studentId, UUID courseId) {
        // 1. Buscar todos los juegos asignados al curso
        List<AssignedGame> juegosOrdenados = assignedGameRepository.findByCursoIdOrderByOrdenAsc(courseId);

        if (juegosOrdenados.isEmpty()) {
            return Optional.empty(); // No hay juegos asignados, se devolverá 404
        }

        // 2. Obtener el último juego completado con su orden
        Optional<Integer> lastCompletedOrder = studentProgressRepository.findLastCompletedGameOrder(studentId, courseId);

        // 3. Buscar el siguiente juego con un orden mayor
        // 4. Si no hay juegos completados, devolver el primer juego
        return lastCompletedOrder.map(integer -> juegosOrdenados.stream()
                .filter(juego -> juego.getOrden() > integer)
                .findFirst()).orElseGet(() -> Optional.of(juegosOrdenados.get(0)));
    }


    public AssignedGame actualizarOrden(UUID assignedGameId, int nuevoOrden) {
        AssignedGame assignedGame = assignedGameRepository.findById(assignedGameId)
                .orElseThrow(() -> new RuntimeException("AssignedGame no encontrado"));

        assignedGame.setOrden(nuevoOrden);
        return assignedGameRepository.save(assignedGame);
    }

}



