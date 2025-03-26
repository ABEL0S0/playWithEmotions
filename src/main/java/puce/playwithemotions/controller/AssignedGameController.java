package puce.playwithemotions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.AssignedGame;
import puce.playwithemotions.entity.AssignedGameRequest;
import puce.playwithemotions.repository.AssignedGameRepository;
import puce.playwithemotions.service.AssignedGameService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/assigned-games")
//@CrossOrigin("*") // Permitir peticiones desde otros dominios
public class AssignedGameController {

    private final AssignedGameService assignedGameService;

    @Autowired
    public AssignedGameController(AssignedGameService assignedGameService) {
        this.assignedGameService = assignedGameService;
    }

    // 📌 Asignar un juego a un curso
    @PostMapping
    public ResponseEntity<AssignedGame> assignGameToCourse(@RequestBody AssignedGameRequest request) {
        AssignedGame assignedGame = assignedGameService.assignGameToCourse(
                request.getProfesorId(), request.getCourseId(), request.getGameId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(assignedGame);
    }

    // 📌 Obtener juegos asignados a un curso
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignedGame>> getGamesByCourse(@PathVariable UUID courseId) {
        List<AssignedGame> assignedGames = assignedGameService.getGamesByCourse(courseId);
        return ResponseEntity.ok(assignedGames);
    }

    // 📌 Desasignar un juego de un curso
    @DeleteMapping("/{courseId}/{gameId}")
    public ResponseEntity<String> unassignGame(@PathVariable UUID courseId, @PathVariable UUID gameId) {
        assignedGameService.unassignGameFromCourse(courseId, gameId);
        return ResponseEntity.ok("Juego desasignado correctamente.");
    }

    // 📌 Obtener juegos asignados por un profesor
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<AssignedGame>> getGamesByProfesor(@PathVariable UUID profesorId) {
        List<AssignedGame> assignedGames = assignedGameService.getGamesByProfesor(profesorId);
        return ResponseEntity.ok(assignedGames);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<AssignedGame>> getUnlockedGames(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        List<AssignedGame> assignedGames = assignedGameService.getUnlockedGames(studentId, courseId);
        return ResponseEntity.ok(assignedGames);
    }

    @GetMapping("/next")
    public ResponseEntity<?> getNextGame(@RequestParam UUID estudianteId, @RequestParam UUID cursoId) {
        if (estudianteId == null || cursoId == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Faltan parámetros estudianteId o cursoId"));
        }

        Optional<AssignedGame> nextGame = assignedGameService.getNextGameForStudent(estudianteId, cursoId);

        return nextGame.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((AssignedGame) Map.of("message", "No hay más juegos disponibles")));
    }


    @PutMapping("/{id}/orden")
    public ResponseEntity<AssignedGame> actualizarOrden(@PathVariable UUID id, @RequestParam int nuevoOrden) {
        return ResponseEntity.ok(assignedGameService.actualizarOrden(id, nuevoOrden));
    }
}

