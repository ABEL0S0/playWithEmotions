package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.Certificate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    List<Certificate> findByEstudianteId(UUID estudianteId);

    @Query("SELECT c FROM Certificate c WHERE c.estudiante.id IN " +
            "(SELECT sc.estudiante.id FROM StudentCourse sc WHERE sc.curso.profesor.id = :profesorId)")
    List<Certificate> findCertificatesByProfessor(@Param("profesorId") UUID profesorId);

    @Query("SELECT c FROM Certificate c WHERE c.estudiante.id = :estudianteId AND c.juego.id = :gameId")
    Optional<Certificate> findByEstudianteAndGame(@Param("estudianteId") UUID estudianteId, @Param("gameId") UUID gameId);
}

