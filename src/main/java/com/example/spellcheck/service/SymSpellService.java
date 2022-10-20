package com.example.spellcheck.service;

import io.gitlab.rxp90.jsymspell.SymSpell;
import io.gitlab.rxp90.jsymspell.api.SuggestItem;
import io.gitlab.rxp90.jsymspell.exceptions.NotInitializedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.gitlab.rxp90.jsymspell.Verbosity.CLOSEST;

@Component
@RequiredArgsConstructor
public class SymSpellService {

    private final SymSpell symSpell;

    public List<SuggestItem> check(final String term) throws NotInitializedException {
        return symSpell.lookup(term, CLOSEST);
    }

}
