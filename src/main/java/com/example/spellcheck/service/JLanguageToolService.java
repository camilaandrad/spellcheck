package com.example.spellcheck.service;

import com.example.spellcheck.dto.JLanguageToolResponse;
import lombok.RequiredArgsConstructor;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.languagetool.JLanguageTool.ParagraphHandling.NORMAL;

@Component
@RequiredArgsConstructor
public class JLanguageToolService {

    private final JLanguageTool jLanguageTools;

    public JLanguageToolResponse check(final String term) throws IOException {
        final List<RuleMatch> results = jLanguageTools.check(term, false, NORMAL);
        if (results.isEmpty()) {
            return JLanguageToolResponse.builder().build();
        }
        final List<String> errorsMessage = new ArrayList<>();
        final List<List<String>> suggestions = new ArrayList<>();
        for (final RuleMatch match : results) {
           errorsMessage.add(match.getShortMessage().isEmpty() ? match.getMessage() : match.getShortMessage());
           suggestions.add(match.getSuggestedReplacements());
        }
        return JLanguageToolResponse.builder()
                .suggestions(suggestions)
                .errorMessage(errorsMessage)
                .build();
    }

}
