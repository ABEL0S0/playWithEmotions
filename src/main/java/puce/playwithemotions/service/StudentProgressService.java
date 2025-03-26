package puce.playwithemotions.service;

import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.AssignedGame;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.StudentProgress;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.AssignedGameRepository;
import puce.playwithemotions.repository.StudentProgressRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentProgressService {

    private final StudentProgressRepository studentProgressRepository;
    private final AssignedGameRepository assignedGameRepository;


    public StudentProgressService(StudentProgressRepository studentProgressRepository, AssignedGameRepository assignedGameRepository) {
        this.studentProgressRepository = studentProgressRepository;
        this.assignedGameRepository = assignedGameRepository;
    }

    public StudentProgress createProgress(StudentProgress studentProgress) {
        return studentProgressRepository.save(studentProgress);
    }

    public StudentProgress updateProgress(UUID id, StudentProgress updatedProgress) {
        return studentProgressRepository.findById(id).map(progress -> {
            progress.setNivelActual(updatedProgress.getNivelActual());
            return studentProgressRepository.save(progress);
        }).orElseThrow(() -> new RuntimeException("Progreso no encontrado"));
    }

    public StudentProgress completeGame(UUID id) {
        return studentProgressRepository.findById(id).map(progress -> {
            progress.setCompletado(true);
            progress.setFechaCompletado(LocalDateTime.now());
            return studentProgressRepository.save(progress);
        }).orElseThrow(() -> new RuntimeException("Progreso no encontrado"));
    }

    public Optional<StudentProgress> getStudentProgress(UUID studentId, UUID gameId) {
        return studentProgressRepository.findByEstudianteIdAndJuegoId(studentId, gameId);
    }

    public List<StudentProgress> getAllProgressByStudent(UUID studentId) {
        return studentProgressRepository.findByEstudianteId(studentId);
    }

    public List<StudentProgress> getStudentsWhoCompletedGame(UUID gameId) {
        return studentProgressRepository.findByJuegoIdAndCompletadoTrue(gameId);
    }

    // Obtener el progreso de un estudiante en un curso
    public List<StudentProgress> getProgressByStudentAndCourse(UUID studentId, UUID cursoId) {
        return studentProgressRepository.findByEstudianteIdAndCursoId(studentId, cursoId);
    }

    // Obtener el progreso de todos los estudiantes en un curso
    public List<StudentProgress> getProgressByCourse(UUID cursoId) {
        return studentProgressRepository.findByCursoId(cursoId);
    }

    public boolean markGameAsCompleted(UUID studentId, UUID courseId, UUID gameId) {
        // 1. Buscar si ya existe progreso registrado para este estudiante y juego
        Optional<StudentProgress> existingProgress = studentProgressRepository.findByEstudianteIdAndJuegoId(studentId, gameId);

        StudentProgress progress;
        if (existingProgress.isPresent()) {
            // 2. Si ya existe, actualizarlo a completado
            progress = existingProgress.get();
        } else {
            // 3. Si no existe, crear un nuevo registro de progreso
            Optional<AssignedGame> assignedGame = assignedGameRepository.findById(gameId);
            if (assignedGame.isEmpty()) {
                return false; // No se encontró el juego asignado al curso
            }

            progress = new StudentProgress();
            progress.setEstudiante(new User(studentId)); // Se asume que User es la entidad de estudiante
            progress.setCurso(new Course(courseId));
            progress.setJuego(assignedGame.get().getJuego());
            progress.setNivelActual(1); // Se puede ajustar según la lógica del juego
        }

        // 4. Marcar como completado
        progress.setCompletado(true);
        progress.setFechaCompletado(LocalDateTime.now());
        studentProgressRepository.save(progress);

        return true;
    }
}


