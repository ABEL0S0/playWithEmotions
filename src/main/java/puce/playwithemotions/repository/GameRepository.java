package puce.playwithemotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import puce.playwithemotions.entity.Game;

import java.util.UUID;
@Repository
public interface GameRepository  extends JpaRepository<Game, UUID> {
}
