package no.even.card.game.app.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.even.card.game.app.domain.CardDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class ScryfallCardConsumer {
    private static final String SCRYFALL_BASE_URL = "https://api.scryfall.com";
    private static final String URL_PATH_CARDS = "cards";
    private static final String URL_PATH_SEARCH = "search";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<CardDetails> getCardsForSetCode(String setCode) throws IOException {
        List<CardDetails> cards = new ArrayList<>();

        ResponseEntity<String> content = restTemplate.exchange(createRequestEntityForSet(setCode), String.class);
        if (!isNull(content.getBody())) {
            Boolean hasMore;
            JsonNode scryfallResponseJsonNode = objectMapper.readTree(content.getBody());
            do {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<CardDetails> cardsForResponse = getCardsFromScryfallResponse(scryfallResponseJsonNode);
                cards.addAll(cardsForResponse);
                hasMore = objectMapper.convertValue(scryfallResponseJsonNode.get("has_more"), Boolean.class);
                if (hasMore) {
                    scryfallResponseJsonNode = computeNextPage(content, scryfallResponseJsonNode);
                }
            } while (hasMore);
            return cards;
        } else {
            return List.of();
        }
    }

    private JsonNode computeNextPage(ResponseEntity<String> content, JsonNode scryfallResponseJsonNode) throws JsonProcessingException {
        String nextPageUrl = objectMapper.convertValue(scryfallResponseJsonNode.get("next_page"), String.class);
        String nextPageFixedUrl = nextPageUrl.replace("%3A", ":");
        ResponseEntity<String> nextContent = restTemplate.exchange(nextPageFixedUrl, HttpMethod.GET, null, String.class);
        return objectMapper.readTree(nextContent.getBody());
    }

    private List<CardDetails> getCardsFromScryfallResponse(JsonNode jsonNode) throws IOException {
        return objectMapper.convertValue(jsonNode.get("data"), new TypeReference<List<CardDetails>>() {
        });
    }

    private RequestEntity<Void> createRequestEntityForSet(String setCode) {
        return RequestEntity.get(createUriForSetCode(setCode)).build();
    }

    private URI createUriForSetCode(String setCode) {
        return UriComponentsBuilder.fromHttpUrl(SCRYFALL_BASE_URL)
                .pathSegment(URL_PATH_CARDS, URL_PATH_SEARCH)
                .queryParam("q", createQueryForSet(setCode))
                .build().toUri();
    }

    private String createQueryForSet(String setCode) {
        return "e:" + setCode;
    }
}
