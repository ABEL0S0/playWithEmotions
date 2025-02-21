package puce.playwithemotions.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignedGameRequest {
    // Getters y setters
    private UUID profesorId;
    private UUID courseId;
    private UUID gameId;

    public void setProfesorId(UUID profesorId) { this.profesorId = profesorId; }

    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public void setGameId(UUID gameId) { this.gameId = gameId; }

    public UUID getProfesorId() {
        return profesorId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
