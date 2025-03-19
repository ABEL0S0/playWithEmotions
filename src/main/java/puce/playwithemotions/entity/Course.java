package puce.playwithemotions.entity;

import jakarta.persistence.*;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    private boolean progresivo; // true = progresivo, false = libre

    @Column(unique = true, nullable = false)
    private String codigo; // Código único para unirse

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    private User profesor; // Relación: Un curso pertenece a un profesor

    public Course() {
    }

    public Course(UUID id, String nombre, String codigo, User profesor, boolean progresivo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.profesor = profesor;
        this.progresivo = progresivo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public User getProfesor() {
        return profesor;
    }

    public void setProfesor(User profesor) {
        this.profesor = profesor;
    }

    public boolean isProgresivo() {
        return progresivo;
    }

    public void setProgresivo(boolean progresivo) {
        this.progresivo = progresivo;
    }
}

