package com.example.spellcheck.service;

import com.example.spellcheck.dto.NorvigsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class NorvigsService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final Map<String, Integer> norvigsDict;

    public NorvigsResponse check(final String word) {
            Optional<String> e1 = known(generateWords(word)).max((a, b) -> norvigsDict.get(a) - norvigsDict.get(b));
            if (e1.isPresent()) {
                return NorvigsResponse.builder()
                        .correctWord(norvigsDict.containsKey(word) ? word : e1.get())
                        .tokens(generateWords(word).collect(Collectors.toList()))
                        .alphabet(ALPHABET)
                        .distance(1)
                        .build();
            }
            Optional<String> e2 = known(generateWords(word).map((w2) -> generateWords(w2)).flatMap((x) -> x)).max((a, b) -> norvigsDict.get(a) - norvigsDict.get(b));
            return NorvigsResponse.builder()
                    .correctWord((e2.isPresent() ? e2.get() : word))
                    .tokens(generateWords(word).collect(Collectors.toList()))
                    .alphabet(ALPHABET)
                    .distance(2)
                    .build();
    }

    private Stream<String> generateWords(final String word) {
        Stream<String> deletes = IntStream.range(0, word.length()).mapToObj((i) -> word.substring(0, i) + word.substring(i + 1));
        Stream<String> replaces = IntStream.range(0, word.length()).mapToObj((i) -> i).flatMap((i) -> ALPHABET.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i + 1)));
        Stream<String> inserts = IntStream.range(0, word.length() + 1).mapToObj((i) -> i).flatMap((i) -> ALPHABET.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i)));
        Stream<String> transposes = IntStream.range(0, word.length() - 1).mapToObj((i) -> word.substring(0, i) + word.substring(i + 1, i + 2) + word.charAt(i) + word.substring(i + 2));
        return Stream.of(deletes, replaces, inserts, transposes).flatMap((x) -> x);
    }

    private Stream<String> known(final Stream<String> words) {
        return words.filter((word) -> norvigsDict.containsKey(word));
    }

}
