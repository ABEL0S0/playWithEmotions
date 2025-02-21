package puce.playwithemotions.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assigned_games")
public class AssignedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    private User profesor; // El profesor que asignó el juego

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course curso; // Curso al que se asignó el juego

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game juego; // Juego asignado

    private LocalDateTime fechaAsignacion = LocalDateTime.now();

    public AssignedGame() {
    }

    public AssignedGame(UUID id, User profesor, Course curso, Game juego, LocalDateTime fechaAsignacion) {
        this.id = id;
        this.profesor = profesor;
        this.curso = curso;
        this.juego = juego;
        this.fechaAsignacion = fechaAsignacion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getProfesor() {
        return profesor;
    }

    public void setProfesor(User profesor) {
        this.profesor = profesor;
    }

    public Course getCurso() {
        return curso;
    }

    public void setCurso(Course curso) {
        this.curso = curso;
    }

    public Game getJuego() {
        return juego;
    }

    public void setJuego(Game juego) {
        this.juego = juego;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}

