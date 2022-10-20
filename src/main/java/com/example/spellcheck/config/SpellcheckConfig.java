package com.example.spellcheck.config;

import io.gitlab.rxp90.jsymspell.SymSpell;
import io.gitlab.rxp90.jsymspell.SymSpellBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpellcheckConfig {

    @Bean
    public SymSpell symspell() throws IOException {
        return new SymSpellBuilder()
                .setUnigramLexicon(unigrams())
                .setMaxDictionaryEditDistance(2)
                .createSymSpell();
    }

    private Map<String, Long> unigrams() throws IOException {
        return Files.lines(Paths.get(ClassLoader.getSystemResource("corpus.txt").getPath()))
                .map(line -> line.split(","))
                .collect(Collectors.toMap(tokens -> tokens[0], tokens -> Long.parseLong(tokens[1])));
    }

}
