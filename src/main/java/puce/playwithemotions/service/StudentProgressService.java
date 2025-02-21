package puce.playwithemotions.service;

import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.StudentProgress;
import puce.playwithemotions.repository.StudentProgressRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentProgressService {

    private final StudentProgressRepository studentProgressRepository;

    public StudentProgressService(StudentProgressRepository studentProgressRepository) {
        this.studentProgressRepository = studentProgressRepository;
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
}

