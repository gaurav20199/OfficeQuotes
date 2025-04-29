package com.project.officequiz.repository;

import com.project.officequiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    @Query(value = """
                SELECT id FROM question
                WHERE is_popular = true
                ORDER BY RANDOM()
                LIMIT 5;     
            """,nativeQuery = true)
    List<Integer> findRandomQuestionsIds();

    @Query(value = """
                    SELECT q from Question q where q.id in :questionIds
                    """)
    List<Question> findQuoteWithQuoteIds(List<Integer> questionIds);
}
