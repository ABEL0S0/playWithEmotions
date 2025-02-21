package puce.playwithemotions.service;

import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.Game;
import puce.playwithemotions.repository.GameRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> getGameById(UUID id) {
        return gameRepository.findById(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game updateGame(UUID id, Game gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setNombre(gameDetails.getNombre());
                    game.setDescripcion(gameDetails.getDescripcion());
                    game.setDificultad(gameDetails.getDificultad());
                    return gameRepository.save(game);
                }).orElseThrow(() -> new RuntimeException("Juego no encontrado"));
    }

    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}

