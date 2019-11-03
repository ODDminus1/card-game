package no.even.card.game.app.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
