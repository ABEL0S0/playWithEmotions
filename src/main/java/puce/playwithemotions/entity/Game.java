package puce.playwithemotions.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    @Lob
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Difficulty dificultad; // FÁCIL, MEDIO, DIFÍCIL

    public Game() {
    }
    public Game(String nombre, String descripcion, Difficulty dificultad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
    }

    public Game(UUID id, String nombre, String descripcion, Difficulty dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Difficulty getDificultad() {
        return dificultad;
    }

    public void setDificultad(Difficulty dificultad) {
        this.dificultad = dificultad;
    }
}

