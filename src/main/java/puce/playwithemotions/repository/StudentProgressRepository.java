package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.StudentProgress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentProgressRepository extends JpaRepository<StudentProgress, UUID> {
    List<StudentProgress> findByEstudianteId(UUID estudianteId);

    Optional<StudentProgress> findByEstudianteIdAndJuegoId(UUID estudianteId, UUID juegoId);

    List<StudentProgress> findByJuegoIdAndCompletadoTrue(UUID juegoId);

    List<StudentProgress> findByEstudianteIdAndCursoId(UUID estudianteId, UUID cursoId); // Nuevo método

    List<StudentProgress> findByCursoId(UUID cursoId); // Obtener el progreso de todos los estudiantes en un curso

    @Query("SELECT MAX(ag.orden) FROM StudentProgress sp " +
            "JOIN AssignedGame ag ON sp.curso = ag.curso AND sp.juego = ag.juego " +
            "WHERE sp.estudiante.id = :estudianteId AND sp.curso.id = :cursoId AND sp.completado = true")
    Optional<Integer> findLastCompletedGameOrder(@Param("estudianteId") UUID estudianteId, @Param("cursoId") UUID cursoId);
}