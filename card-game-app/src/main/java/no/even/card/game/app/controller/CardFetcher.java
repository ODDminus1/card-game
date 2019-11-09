package no.even.card.game.app.controller;

import no.even.card.game.app.repository.Card;
import no.even.card.game.app.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;


@Component
public class CardFetcher {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> fetchRandomCards(@RequestParam(required = false, defaultValue = "1") Integer numberOfCards, @RequestParam(required = false, name = "types") List<String> includedTypes, @RequestParam(required = false, name = "exclude") List<Long> excludedCards) {
        List<Long> range = getCardIdsInRandomOrder();

        List<Card> cardsToReturn = new ArrayList<>();
        int iterator = 0;
        while (cardsToReturn.size() < numberOfCards) {
            Optional<Card> card = cardRepository.findById(range.get(iterator));
            if (card.isPresent() && shouldBeIncluded(card.get(), includedTypes, excludedCards)) {
                cardsToReturn.add(card.get());
            }
            iterator++;
            if (iterator == range.size()) {
                throw new RuntimeException("Failed to find enough cards for query");
            }
        }

        return cardsToReturn;
    }

    private List<Long> getCardIdsInRandomOrder() {
        Long maxId = cardRepository.getMaxId();
        List<Long> range = range(maxId);
        Collections.shuffle(range);
        return range;
    }

    private boolean shouldBeIncluded(Card card, List<String> includedTypes, List<Long> excludedCards) {
        return !isExcluded(card, excludedCards) && isIncludedType(card, includedTypes);
    }

    private boolean isExcluded(Card card, List<Long> excludedCards) {
        return !isNull(excludedCards) && excludedCards.contains(card.getMultiverseId());
    }

    private boolean isIncludedType(Card card, List<String> includedTypes) {
        return (includedTypes == null || includedTypes.stream().anyMatch(type -> card.getTypeLine().toLowerCase().contains(type)));
    }

    private List<Long> range(Long to) {
        List<Long> longList = new ArrayList<>();
        for (Long i = 1L; i <= to; i++) {
            longList.add(i);
        }
        return longList;
    }
}
