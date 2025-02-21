package puce.playwithemotions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.Game;
import puce.playwithemotions.service.GameService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
//@CrossOrigin("*") // Permitir peticiones desde otros dominios
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // 1. Crear un nuevo juego
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game newGame = gameService.createGame(game);
        return ResponseEntity.ok(newGame);
    }

    // 2. Obtener juego por ID
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable UUID id) {
        Optional<Game> game = gameService.getGameById(id);
        return game.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 3. Obtener todos los juegos
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    // 4. Actualizar un juego
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable UUID id, @RequestBody Game gameDetails) {
        Game updatedGame = gameService.updateGame(id, gameDetails);
        return ResponseEntity.ok(updatedGame);
    }

    // 5. Eliminar un juego
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}