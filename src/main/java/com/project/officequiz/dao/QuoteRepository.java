package com.project.officequiz.dao;

import com.project.officequiz.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote,Integer>{
	
	@Query(value = "SELECT * FROM quote Order by rand() Limit 5",nativeQuery = true)
	List<Quote> findRandomQuestions();
			
}
