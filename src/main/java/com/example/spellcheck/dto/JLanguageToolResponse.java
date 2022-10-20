package com.example.spellcheck.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JLanguageToolResponse {

    private List<List<String>> suggestions;
    private List<String> errorMessage;

}
