package com.project.officequiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record QuoteDTO(String quote,
                       @JsonProperty("options")
                       List<OptionsDTO> optionsDTO,
                       int answer) {
}
