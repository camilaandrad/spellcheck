package com.example.spellcheck.config;

import io.gitlab.rxp90.jsymspell.SymSpell;
import io.gitlab.rxp90.jsymspell.SymSpellBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpellcheckConfig {

    @Bean
    public SymSpell symspell() {
        return new SymSpellBuilder()
                .setUnigramLexicon(unigrams())
                .setMaxDictionaryEditDistance(2)
                .createSymSpell();
    }

    private Map<String, Long> unigrams() {
        final ClassPathResource resource = new ClassPathResource("corpus.txt");
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines()
                    .map(line -> line.split(","))
                    .collect(Collectors.toMap(tokens -> tokens[0], tokens -> Long.parseLong(tokens[1])));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Collections.emptyMap();
    }

}
