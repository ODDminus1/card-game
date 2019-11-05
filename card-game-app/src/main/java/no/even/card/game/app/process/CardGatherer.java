package no.even.card.game.app.process;

import no.even.card.game.app.consumer.ScryfallCardConsumer;
import no.even.card.game.app.domain.CardDetails;
import no.even.card.game.app.repository.Card;
import no.even.card.game.app.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CardGatherer {
    @Autowired
    private ScryfallCardConsumer cardConsumer;

    @Autowired
    private CardRepository cardRepository;

    private static final List<String> interestingSetCodes =
            List.of(
                    "RTR",
                    "GTC",
                    "DGM",
                    "THS",
                    "BNG",
                    "JOU",
                    "KTK",
                    "FRF",
                    "DTK",
                    "BFZ",
                    "OGW",
                    "SOI",
                    "EMN",
                    "KLD",
                    "AER",
                    "AKH",
                    "HOU",
                    "XLN",
                    "RIX",
                    "DOM",
                    "GRN",
                    "RNA",
                    "WAR",
                    "ELD"
            );

    @EventListener(classes = ContextRefreshedEvent.class)
    public void processStarter() throws IOException {
        for (String interestingSetCode : interestingSetCodes) {
            saveCardsForSetcode(interestingSetCode);
        }
    }

    private void saveCardsForSetcode(String setCode) throws IOException {
        List<CardDetails> cards = cardConsumer.getCardsForSetCode(setCode);
        saveToRepository(cards);
    }

    private void saveToRepository(List<CardDetails> cards) {
        List<Card> collect = cards.stream().map(c -> Card.builder()
                .name(c.getName())
                .multiverseId(c.getMultiverseIds().stream().max(Comparator.naturalOrder()).orElse(-1L))
                .imageUrl(c.getImageUrl().getNormal())
                .typeLine(c.getTypesLine())
                .build())
                .collect(toList());
        cardRepository.saveAll(collect);
    }
}
