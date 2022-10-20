package com.example.spellcheck.service;

import com.example.spellcheck.dto.JLanguageToolResponse;
import lombok.RequiredArgsConstructor;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        final RuleMatch firstRule = results.get(0);
        return JLanguageToolResponse.builder()
                .suggestions(firstRule.getSuggestedReplacements())
                .errorMessage(firstRule.getShortMessage().isEmpty() ? firstRule.getMessage() : firstRule.getShortMessage())
                .build();
    }

}
