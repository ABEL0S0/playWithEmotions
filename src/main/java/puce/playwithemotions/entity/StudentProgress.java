package puce.playwithemotions.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_progress")
public class StudentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User estudiante;

    @ManyToOne
    private Game juego;

    @ManyToOne
    private Course curso;

    private int nivelActual;
    private boolean completado;
    private LocalDateTime fechaCompletado;

    public StudentProgress() {
    }

    public StudentProgress(UUID id, User estudiante, Game juego, Course curso, int nivelActual, boolean completado, LocalDateTime fechaCompletado) {
        this.id = id;
        this.estudiante = estudiante;
        this.juego = juego;
        this.curso = curso;
        this.nivelActual = nivelActual;
        this.completado = completado;
        this.fechaCompletado = fechaCompletado;
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

    public Course getCurso() {
        return curso;
    }

    public void setCurso(Course curso) {
        this.curso = curso;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(LocalDateTime fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }
}

