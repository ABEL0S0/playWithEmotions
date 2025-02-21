package puce.playwithemotions.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User estudiante;

    @ManyToOne
    private Game juego;

    private LocalDateTime fechaEmision;

    @Lob
    private byte[] archivoPdf; // Se almacena el PDF en la base de datos

    public Certificate() {
    }

    public Certificate(UUID id, User estudiante, Game juego, LocalDateTime fechaEmision, byte[] archivoPdf) {
        this.id = id;
        this.estudiante = estudiante;
        this.juego = juego;
        this.fechaEmision = fechaEmision;
        this.archivoPdf = archivoPdf;
    }

    public Certificate(User estudiante, Game juego, LocalDateTime fechaEmision, byte[] archivoPdf) {
        this.estudiante = estudiante;
        this.juego = juego;
        this.fechaEmision = fechaEmision;
        this.archivoPdf = archivoPdf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(User estudiante) {
        this.estudiante = estudiante;
    }

    public Game getJuego() {
        return juego;
    }

    public void setJuego(Game juego) {
        this.juego = juego;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public byte[] getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(byte[] archivoPdf) {
        this.archivoPdf = archivoPdf;
    }
}

