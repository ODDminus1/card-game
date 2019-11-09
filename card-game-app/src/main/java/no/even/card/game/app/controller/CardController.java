package no.even.card.game.app.controller;

import no.even.card.game.app.repository.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardFetcher cardFetcher;

    @GetMapping
    public List<Card> getCards(
            @RequestParam(required = false, defaultValue = "1") Integer numberOfCards,
            @RequestParam(required = false, name = "types") List<String> includedTypes,
            @RequestParam(required = false, name = "exclude") List<Long> excludedCards
    ) {
        return cardFetcher.fetchRandomCards(numberOfCards, includedTypes, excludedCards);
    }

}
