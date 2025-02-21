package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.StudentCourse;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, UUID> {

    List<StudentCourse> findByEstudianteId(UUID estudianteId);

    List<StudentCourse> findByCursoId(UUID cursoId);

    boolean existsByEstudianteIdAndCursoId(UUID estudianteId, UUID cursoId);

    boolean existsByEstudianteId(UUID estudianteId);

    @Query("SELECT sc.curso FROM StudentCourse sc WHERE sc.estudiante.id = :studentId")
    List<Course> findCoursesByEstudianteId(@Param("studentId") UUID studentId);
}


