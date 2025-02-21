package puce.playwithemotions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.StudentCourse;
import puce.playwithemotions.service.StudentCourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-courses")
//@CrossOrigin("*") // Permitir peticiones desde otros dominios
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @Autowired
    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    // 1️⃣ Inscribir un estudiante en un curso usando el código del curso
    @PostMapping("/enroll/{estudianteId}/{codigoCurso}")
    public ResponseEntity<StudentCourse> enrollStudent(
            @PathVariable UUID estudianteId, @PathVariable String codigoCurso) {
        return ResponseEntity.ok(studentCourseService.enrollStudent(estudianteId, codigoCurso));
    }

    // 2️⃣ Obtener los cursos en los que está inscrito un estudiante
    @GetMapping("/student/{estudianteId}")
    public ResponseEntity<List<StudentCourse>> getStudentCourses(@PathVariable UUID estudianteId) {
        return ResponseEntity.ok(studentCourseService.getStudentCourses(estudianteId));
    }

    // 3️⃣ Obtener los estudiantes inscritos en un curso
    @GetMapping("/course/{cursoId}")
    public ResponseEntity<List<StudentCourse>> getCourseStudents(@PathVariable UUID cursoId) {
        return ResponseEntity.ok(studentCourseService.getCourseStudents(cursoId));
    }
}
