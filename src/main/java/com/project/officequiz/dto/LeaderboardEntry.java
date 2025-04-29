package com.project.officequiz.dto;

import java.time.LocalDateTime;

public record LeaderboardEntry(Integer rank, String userName, Integer score,String quizType, LocalDateTime completedAt) {
}