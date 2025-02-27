package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.AssignedGame;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.Game;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignedGameRepository extends JpaRepository<AssignedGame, UUID> {

    List<AssignedGame> findByCurso(Course curso);

    List<AssignedGame> findByProfesorId(UUID profesorId);

    List<AssignedGame> findByCursoIn(List<Course> cursos);

    boolean existsByCursoAndJuego(Course curso, Game juego);

    Optional<AssignedGame> findByCursoAndJuego(Course curso, Game juego);

}

