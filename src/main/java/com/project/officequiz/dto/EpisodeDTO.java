package com.project.officequiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;

public record EpisodeDTO(String title, String description, double imdbRating,
                         int totalVotes, String writtenBy, String directedBy,
                         @JsonProperty("episode")
                         short episodeNo,
                         @JsonProperty("airDate")
                         Date releaseDate,
                         @JsonProperty("season")
                         short seasonNumber) {
}
