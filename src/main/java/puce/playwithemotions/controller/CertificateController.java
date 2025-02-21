package puce.playwithemotions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import puce.playwithemotions.entity.Certificate;
import puce.playwithemotions.service.CertificateService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/certificates")
//@CrossOrigin("*") // Permitir peticiones desde otros dominios
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    // 📌 Generar un certificado para un estudiante
    @PostMapping("/generate")
    public ResponseEntity<Certificate> generateCertificate(
            @RequestParam UUID studentId,
            @RequestParam UUID gameId) {

        Certificate certificate = certificateService.generateCertificate(studentId, gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificate);
    }

    // 📌 Obtener certificados de un estudiante
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Certificate>> getCertificatesByStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(certificateService.getCertificatesByStudent(studentId));
    }

    // 📌 Obtener certificados de los estudiantes de un profesor
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Certificate>> getCertificatesByProfessor(@PathVariable UUID profesorId) {
        return ResponseEntity.ok(certificateService.getCertificatesByProfessor(profesorId));
    }

    // 📌 Obtener un certificado en PDF
    @GetMapping("/{certificateId}/pdf")
    public ResponseEntity<byte[]> getCertificatePdf(@PathVariable UUID certificateId) {
        byte[] pdfContent = certificateService.getCertificatePdf(certificateId);
        if (pdfContent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }
}


