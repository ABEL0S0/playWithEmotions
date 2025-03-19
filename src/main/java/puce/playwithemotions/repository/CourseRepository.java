package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByCodigo(String codigo);
    List<Course> findByProfesor(User profesor);
    List<Course> findByProgresivo(boolean progresivo);
}