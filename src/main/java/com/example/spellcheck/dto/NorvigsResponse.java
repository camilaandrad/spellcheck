package com.example.spellcheck.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NorvigsResponse {

    private String correctWord;
    private List<String> tokens;
    private String alphabet;
    private int distance;

}
