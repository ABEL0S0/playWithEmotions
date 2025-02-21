package puce.playwithemotions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
//@CrossOrigin("*") // Permitir peticiones desde otros dominios
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 1️⃣ Crear un curso
    @PostMapping("/create/{profesorId}")
    public ResponseEntity<Course> createCourse(@RequestBody Course curso, @PathVariable UUID profesorId) {
        Course nuevoCurso = courseService.createCourse(curso, profesorId);
        return ResponseEntity.ok(nuevoCurso);
    }

    // 2️⃣ Obtener curso por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Course> getCourseByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(courseService.getCourseByCodigo(codigo));
    }

    // 3️⃣ Obtener todos los cursos de un profesor
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Course>> getCoursesByProfesor(@PathVariable UUID profesorId) {
        return ResponseEntity.ok(courseService.getCoursesByProfesor(profesorId));
    }
}
