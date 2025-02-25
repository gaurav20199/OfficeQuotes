package com.project.officequiz.dao;

import com.project.officequiz.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote,Integer>{
	
	@Query(value = "SELECT DISTINCT id FROM quote ORDER BY RAND() limit 20",nativeQuery = true)
	List<Integer> findRandomQuestionsIds();

	@Query(value = "SELECT q from Quote q where q.id in :quoteIds")
	List<Quote> findQuoteWithQuoteIds(List<Integer> quoteIds);
			
}
