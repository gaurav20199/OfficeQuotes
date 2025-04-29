package com.project.officequiz.repository;

import com.project.officequiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("SELECT q FROM Quiz q WHERE q.completedAt IS NOT NULL ORDER BY q.score DESC, q.completedAt ASC")
    List<Quiz> findTopScores();

    @Query("SELECT q FROM Quiz q WHERE q.completedAt IS NOT NULL AND q.quizType = :quizType ORDER BY q.score DESC, q.completedAt ASC")
    List<Quiz> findTopScoresByQuizType(Short quizType);

    @Query("SELECT q FROM Quiz q WHERE q.completedAt IS NOT NULL AND q.seasonId = :seasonId ORDER BY q.score DESC, q.completedAt ASC")
    List<Quiz> findTopScoresBySeason(Integer seasonId);
}