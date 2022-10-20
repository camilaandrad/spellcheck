package com.example.spellcheck.config;

import io.gitlab.rxp90.jsymspell.SymSpell;
import io.gitlab.rxp90.jsymspell.SymSpellBuilder;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BrazilianPortuguese;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SpellcheckConfig {

    @Bean
    public SymSpell symspell() {
        return new SymSpellBuilder()
                .setUnigramLexicon(unigrams())
                .setMaxDictionaryEditDistance(2)
                .createSymSpell();
    }

    @Bean
    public JLanguageTool jLanguageTools() {
        return new JLanguageTool(new BrazilianPortuguese());
    }

    @Bean
    public Map<String, Integer> norvigsDict() throws IOException {
        final Map<String, Integer> dict = new HashMap();
        final ClassPathResource resource = new ClassPathResource("corpus-norvigs.txt");
        Stream.of(
                new String(resource.getInputStream().readAllBytes())
                        .toLowerCase().
                        replaceAll(" ", "").
                        split("\n")).
                forEach((word) -> {
                    dict.compute(word, (k, v) -> v == null ? 1 : v + 1);
                });
        return dict;
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
