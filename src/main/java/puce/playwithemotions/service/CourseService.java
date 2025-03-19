package puce.playwithemotions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.Role;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.CourseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public Course createCourse(Course curso, UUID profesorId) {
        User profesor = userService.getUserById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("El profesor no existe."));

        if (profesor.getRole() != Role.PROFESOR) {
            throw new IllegalArgumentException("Solo los profesores pueden crear cursos.");
        }

        curso.setProfesor(profesor);
        curso.setCodigo(UUID.randomUUID().toString().substring(0, 6));
        return courseRepository.save(curso);
    }

    public void deleteCourse(UUID cursoId) {
        Course curso = courseRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));
        courseRepository.delete(curso);
    }

    public Course getCourseByCodigo(String codigo) {
        return courseRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un curso con este código."));
    }

    public List<Course> getCoursesByProfesor(UUID profesorId) {
        User profesor = userService.getUserById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("El profesor no existe."));
        return courseRepository.findByProfesor(profesor);
    }

    // ✅ Método para actualizar si el curso es progresivo o libre
    public Course updateProgresivo(UUID cursoId, boolean progresivo) {
        Course curso = courseRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));

        curso.setProgresivo(progresivo);
        return courseRepository.save(curso);
    }
}
