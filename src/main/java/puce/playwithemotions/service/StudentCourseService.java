package puce.playwithemotions.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.Course;
import puce.playwithemotions.entity.StudentCourse;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.CourseRepository;
import puce.playwithemotions.repository.StudentCourseRepository;
import puce.playwithemotions.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public StudentCourse enrollStudent(UUID estudianteId, String codigoCurso) {
        User estudiante = userRepository.findById(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        Course curso = courseRepository.findByCodigo(codigoCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        // Verificar si ya está inscrito
        if (studentCourseRepository.existsByEstudianteIdAndCursoId(estudianteId, curso.getId())) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en este curso.");
        }

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setEstudiante(estudiante);
        studentCourse.setCurso(curso);
        return studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourse> getStudentCourses(UUID estudianteId) {
        return studentCourseRepository.findByEstudianteId(estudianteId);
    }

    public List<StudentCourse> getCourseStudents(UUID cursoId) {
        return studentCourseRepository.findByCursoId(cursoId);
    }
}

