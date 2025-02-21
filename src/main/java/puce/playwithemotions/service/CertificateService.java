package puce.playwithemotions.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puce.playwithemotions.entity.Certificate;
import puce.playwithemotions.entity.Game;
import puce.playwithemotions.entity.User;
import puce.playwithemotions.repository.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final AssignedGameRepository assignedGameRepository;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository,
                              UserRepository userRepository,
                              GameRepository gameRepository,
                              StudentCourseRepository studentCourseRepository,
                              AssignedGameRepository assignedGameRepository) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.assignedGameRepository = assignedGameRepository;
    }

    // 📌 1. Generar un certificado si el estudiante completó el juego del curso
    public Certificate generateCertificate(UUID studentId, UUID gameId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        // ❌ Evitar duplicados
        if (certificateRepository.findByEstudianteAndGame(studentId, gameId).isPresent()) {
            throw new RuntimeException("El certificado ya ha sido generado para este estudiante y juego.");
        }

        // ✅ Validar si el estudiante pertenece a un curso con este juego asignado
        boolean isEnrolledInCourse = studentCourseRepository.existsByEstudianteId(studentId);

        boolean isGameAssigned = assignedGameRepository.findByCursoIn(
                studentCourseRepository.findCoursesByEstudianteId(studentId)
        ).stream().anyMatch(ag -> ag.getJuego().getId().equals(gameId));


        if (!isEnrolledInCourse || !isGameAssigned) {
            throw new RuntimeException("El estudiante no puede obtener este certificado.");
        }

        byte[] pdfContent = createCertificatePDF(student.getNombre(), game.getNombre());

        Certificate certificate = new Certificate();
        certificate.setEstudiante(student);
        certificate.setJuego(game);
        certificate.setFechaEmision(LocalDateTime.now());
        certificate.setArchivoPdf(pdfContent);

        return certificateRepository.save(certificate);
    }

    // 📌 2. Crear PDF del certificado
    private byte[] createCertificatePDF(String studentName, String gameName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);

            document.add(new Paragraph("CERTIFICADO DE FINALIZACIÓN", titleFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Este certificado se otorga a:", textFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(studentName, new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Por haber completado exitosamente el juego:", textFont));
            document.add(new Paragraph(gameName, new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Fecha de emisión: " + LocalDateTime.now().toString(), textFont));

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el certificado en PDF", e);
        }
    }

    // 📌 3. Obtener certificados de un estudiante
    public List<Certificate> getCertificatesByStudent(UUID studentId) {
        return certificateRepository.findByEstudianteId(studentId);
    }

    // 📌 4. Obtener certificados de los estudiantes asignados por un profesor
    public List<Certificate> getCertificatesByProfessor(UUID profesorId) {
        return certificateRepository.findCertificatesByProfessor(profesorId);
    }

    // 📌 5. Obtener un certificado por ID
    public Optional<Certificate> getCertificateById(UUID id) {
        return certificateRepository.findById(id);
    }

    // 📌 6. Obtener certificados en PDF desde la base de datos
    public byte[] getCertificatePdf(UUID certificateId) {
        return certificateRepository.findById(certificateId)
                .map(Certificate::getArchivoPdf)
                .orElse(null);
    }
}


