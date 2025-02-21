package puce.playwithemotions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.StudentProgress;
import puce.playwithemotions.service.StudentProgressService;

import java.util.List;
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

    // 1. Registrar un nuevo progreso
    @PostMapping
    public ResponseEntity<StudentProgress> createProgress(@RequestBody StudentProgress studentProgress) {
        StudentProgress newProgress = studentProgressService.createProgress(studentProgress);
        return ResponseEntity.ok(newProgress);
    }

    // 2. Actualizar el nivel de progreso
    @PutMapping("/{id}")
    public ResponseEntity<StudentProgress> updateProgress(@PathVariable UUID id, @RequestBody StudentProgress updatedProgress) {
        StudentProgress progress = studentProgressService.updateProgress(id, updatedProgress);
        return ResponseEntity.ok(progress);
    }

    // 3. Marcar un juego como completado
    @PutMapping("/{id}/complete")
    public ResponseEntity<StudentProgress> completeGame(@PathVariable UUID id) {
        StudentProgress progress = studentProgressService.completeGame(id);
        return ResponseEntity.ok(progress);
    }

    // 4. Obtener el progreso de un estudiante en un juego específico
    @GetMapping("/student/{studentId}/game/{gameId}")
    public ResponseEntity<StudentProgress> getStudentProgress(@PathVariable UUID studentId, @PathVariable UUID gameId) {
        Optional<StudentProgress> progress = studentProgressService.getStudentProgress(studentId, gameId);
        return progress.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 5. Obtener todo el progreso de un estudiante
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentProgress>> getAllProgressByStudent(@PathVariable UUID studentId) {
        List<StudentProgress> progressList = studentProgressService.getAllProgressByStudent(studentId);
        return ResponseEntity.ok(progressList);
    }

    // 6. Obtener todos los estudiantes que han completado un juego
    @GetMapping("/game/{gameId}/completed")
    public ResponseEntity<List<StudentProgress>> getStudentsWhoCompletedGame(@PathVariable UUID gameId) {
        List<StudentProgress> completedProgress = studentProgressService.getStudentsWhoCompletedGame(gameId);
        return ResponseEntity.ok(completedProgress);
    }
}
