package no.even.card.game.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {
    @Query("SELECT coalesce(max(ch.id), 0) FROM Card ch")
    Long getMaxId();

    List<Card> findAllByIdIn(List<Long> subList);
}
