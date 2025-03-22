package com.project.officequiz.dto;

import java.time.LocalDate;

public record SeasonDTO(short seasonId, short number, LocalDate releaseDate,Double avgRating) {
}
