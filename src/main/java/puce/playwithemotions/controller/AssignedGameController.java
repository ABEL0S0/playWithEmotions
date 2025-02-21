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

    // 📌 Obtener juegos asignados por un profesor
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<AssignedGame>> getGamesByProfesor(@PathVariable UUID profesorId) {
        List<AssignedGame> assignedGames = assignedGameService.getGamesByProfesor(profesorId);
        return ResponseEntity.ok(assignedGames);
    }
}

