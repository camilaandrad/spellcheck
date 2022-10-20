package com.example.spellcheck.api;

import com.example.spellcheck.dto.JLanguageToolResponse;
import com.example.spellcheck.service.JLanguageToolService;
import com.example.spellcheck.service.SymSpellService;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("spellcheck")
@RequiredArgsConstructor
public class SpellcheckController {

    private final SymSpellService symSpellService;
    private final JLanguageToolService jLanguageToolService;

    @GetMapping("symspell")
    public ResponseEntity<List<SuggestItem>> symspell(@RequestParam(name= "term") String term) throws NotInitializedException {
        final List<SuggestItem> suggestions = symSpellService.check(term);
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

    @GetMapping("jlangtools")
    public ResponseEntity<JLanguageToolResponse> jLanguageTools(@RequestParam(name= "term") String term) throws IOException {
        final JLanguageToolResponse suggestions = jLanguageToolService.check(term);
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

}
