package com.example.spellcheck.api;

import io.gitlab.rxp90.jsymspell.SymSpell;
import io.gitlab.rxp90.jsymspell.Verbosity;
import io.gitlab.rxp90.jsymspell.api.SuggestItem;
import io.gitlab.rxp90.jsymspell.exceptions.NotInitializedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("check")
@RequiredArgsConstructor
public class SpellcheckController {

    private final SymSpell symSpell;

    @GetMapping("symspell")
    public ResponseEntity<List<SuggestItem>> symspell(@RequestParam(name= "term") String term) throws NotInitializedException {
        final List<SuggestItem> suggestions = symSpell.lookup(term, Verbosity.CLOSEST);
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

}
