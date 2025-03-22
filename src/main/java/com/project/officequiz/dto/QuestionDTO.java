package com.project.officequiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record QuestionDTO(String quote,
                          @JsonProperty(value = "options")
                          Set<OptionsDTO> optionsDTOSet) {
}
