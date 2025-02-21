package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}