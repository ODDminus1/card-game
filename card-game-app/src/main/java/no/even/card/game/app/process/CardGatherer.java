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

    @EventListener(classes = ContextRefreshedEvent.class)
    public void processStarter() throws IOException {
        saveCardsForSetcode("RTR");
        saveCardsForSetcode("KTK");
        saveCardsForSetcode("ELD");
        saveCardsForSetcode("DGM");
        saveCardsForSetcode("MH1");
    }

    private void saveCardsForSetcode(String mh1) throws IOException {
        List<CardDetails> cards = cardConsumer.getCardsForSetCode(mh1);
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
