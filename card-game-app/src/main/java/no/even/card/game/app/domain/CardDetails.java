package no.even.card.game.app.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDetails {
    private String name;

    @JsonAlias("image_uris")
    private ImageUris imageUrl;

    @JsonAlias("multiverse_ids")
    private List<Long> multiverseIds;

    @JsonAlias("type_line")
    private String typesLine;
}
