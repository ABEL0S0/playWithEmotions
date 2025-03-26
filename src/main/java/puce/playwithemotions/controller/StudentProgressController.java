package puce.playwithemotions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.StudentProgress;
import puce.playwithemotions.service.StudentProgressService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-progress")
//@CrossOrigin("*")
public class StudentProgressController {

    private final StudentProgressService studentProgressService;

    public StudentProgressController(StudentProgressService studentProgressService) {
        this.studentProgressService = studentProgressService;
    }

    // Registrar un nuevo progreso
    @PostMapping
    public ResponseEntity<StudentProgress> createProgress(@RequestBody StudentProgress studentProgress) {
        StudentProgress newProgress = studentProgressService.createProgress(studentProgress);
        return ResponseEntity.ok(newProgress);
    }

    // Actualizar el nivel de progreso
    @PutMapping("/{id}")
    public ResponseEntity<StudentProgress> updateProgress(@PathVariable UUID id, @RequestBody StudentProgress updatedProgress) {
        StudentProgress progress = studentProgressService.updateProgress(id, updatedProgress);
        return ResponseEntity.ok(progress);
    }
    // Obtener el progreso de un estudiante en un juego específico
    @GetMapping("/student/{studentId}/game/{gameId}")
    public ResponseEntity<StudentProgress> getStudentProgress(@PathVariable UUID studentId, @PathVariable UUID gameId) {
        Optional<StudentProgress> progress = studentProgressService.getStudentProgress(studentId, gameId);
        return progress.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Obtener todo el progreso de un estudiante
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentProgress>> getAllProgressByStudent(@PathVariable UUID studentId) {
        List<StudentProgress> progressList = studentProgressService.getAllProgressByStudent(studentId);
        return ResponseEntity.ok(progressList);
    }

    // Obtener todos los estudiantes que han completado un juego
    @GetMapping("/game/{gameId}/completed")
    public ResponseEntity<List<StudentProgress>> getStudentsWhoCompletedGame(@PathVariable UUID gameId) {
        List<StudentProgress> completedProgress = studentProgressService.getStudentsWhoCompletedGame(gameId);
        return ResponseEntity.ok(completedProgress);
    }

    // Obtener el progreso de un estudiante en un curso
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<StudentProgress>> getProgressByStudentAndCourse(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        List<StudentProgress> progress = studentProgressService.getProgressByStudentAndCourse(studentId, courseId);
        return ResponseEntity.ok(progress);
    }

    // Obtener el progreso de todos los estudiantes en un curso
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<StudentProgress>> getProgressByCourse(@PathVariable UUID courseId) {
        List<StudentProgress> progressList = studentProgressService.getProgressByCourse(courseId);
        return ResponseEntity.ok(progressList);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeGame(@RequestBody UUID studentId, @RequestBody UUID courseId, @RequestBody UUID gameId) {
        boolean completado = studentProgressService.markGameAsCompleted(studentId, courseId, gameId);

        if (completado) {
            return ResponseEntity.ok(Map.of("message", "Juego marcado como completado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "No se pudo registrar el progreso"));
        }
    }

}
