package no.even.card.game.app.controller.card;

import no.even.card.game.app.repository.Card;
import no.even.card.game.app.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @GetMapping
    public List<Card> getCards(
            @RequestParam Integer numberOfCards,
            @RequestParam String types
    ) {
        Long maxId = cardRepository.getMaxId();
        List<Long> range = range(maxId);
        Collections.shuffle(range);
        List<String> includedTypes = List.of(types.toLowerCase().split(","));

        List<Card> cardsToReturn = new ArrayList<>();
        int iterator = 0;
        while (cardsToReturn.size() < numberOfCards) {
            Optional<Card> card = cardRepository.findById(range.get(iterator));
            if (card.isPresent() && shouldBeIncluded(card.get(), includedTypes)) {
                cardsToReturn.add(card.get());
            }
            iterator++;
            if (iterator == range.size()) {
                throw new RuntimeException("Failed to find enough cards for query");
            }
        }

        return cardsToReturn;
    }

    private boolean shouldBeIncluded(Card card, List<String> includedTypes) {
        return includedTypes.stream().anyMatch(type -> card.getTypeLine().toLowerCase().contains(type));

    }

    private List<Long> range(Long to) {
        List<Long> longList = new ArrayList<>();
        for (Long i = 1L; i <= to; i++) {
            longList.add(i);
        }
        return longList;
    }
}
